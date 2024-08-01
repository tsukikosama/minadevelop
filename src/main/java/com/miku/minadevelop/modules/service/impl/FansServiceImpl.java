package com.miku.minadevelop.modules.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.common.config.TaskExecutorConfig;
import com.miku.minadevelop.modules.entity.Fans;
import com.miku.minadevelop.modules.event.NotFollowEvent;
import com.miku.minadevelop.modules.mapper.FansMapper;
import com.miku.minadevelop.modules.request.FollowReq;
import com.miku.minadevelop.modules.response.FansResp;
import com.miku.minadevelop.modules.service.IFansService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miku.minadevelop.modules.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.PushBuilder;
import javax.swing.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.miku.minadevelop.modules.utils.RedisKey.FOLLOW_KEY;
import static com.miku.minadevelop.modules.utils.RedisKey.USER_KEY;

/**
 * <p>
 *  服务实现类
 *
 * </p>
 *  用户的关注 有两套方案
 *   一是 使用字段来判断是否有关注  这边使用字段来判断用户是否有关注 减少了插入和删除的频率 但是会占用更多的存储空间 (采用)
 *   二是使用数据来判断是否有关注 如果有这条记录就代表有关注 如果没有这条记录就代表没有关注 但是这个方案 会进行较多的删除插入操作
 * @author miku
 * @since 2024-07-29
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FansServiceImpl extends ServiceImpl<FansMapper, Fans> implements IFansService {


    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public IPage<FansResp> selectPage(IPage<Object> page, Long uid) {
        IPage<FansResp> result = baseMapper.selectCustomPage(page, new QueryWrapper<Fans>().eq("fans.user_id", uid));
        //从redis中查看是否有关注的用户 如果有就把数据添加入结果中
        String redisKey = USER_KEY + uid;

        //@todo 需要从redis中查询到 我关注的用户
        Set<String> membersFans = stringRedisTemplate.opsForSet().members(redisKey);
        log.info("关注的用户数量为：{}",membersFans);
        List<FansResp> records = result.getRecords();
        records.forEach(item -> {
            Integer userId = Integer.parseInt(item.getUserId());
            if(membersFans.contains(userId.toString())){
                log.info("寻找到了相互关注的{}",item);
                item.setIsBackFollow(1);
            }
        });
        result.setRecords(records);
        return result;
    }

    /**
     * 取消关注
     * @param req
     */
    @Override
    public void notFollow(FollowReq req){
//        Fans fans = BeanUtil.copyProperties(req, Fans.class);
//        fans.setIsFollow(0);
//        save(fans);
        this.update(Wrappers.<Fans>lambdaUpdate().eq(Fans::getUserId,req.getUserId()).
                eq(Fans::getFollowUserId,req.getFollowUserId()).set(Fans::getIsFollow,0));
    }

    @Override
    public IPage<FansResp> selectFansListByUid(IPage<Object> page, Long uid) {
        IPage<FansResp> result = baseMapper.selectFansListByUid(page,
                Wrappers.<Fans>lambdaUpdate().eq(Fans::getFollowUserId,uid.toString()).eq(Fans::getIsFollow,1));
        return result;
    }

    /**
     * 先从redis中查询是否关注过 如果没有关注过就去查询数据库
     * 如果数据库中没有查询到
     * 就把当前的数据写入数据库 然后存入redis中
     * 如果数据库中查到了
     * 就修改数据库中的数据 然后存入redis中
     * 这样可以mysql
     * @param req
     */
    @Override
    public void follow(FollowReq req) {
        //获取关注用户的id 拼接rediskey
        String followKey = FOLLOW_KEY + req.getFollowUserId();
        //获取全部的粉丝
        Set<String> fansMembers = stringRedisTemplate.opsForSet().members(followKey);
        //判断是否存在当前的用户id
        if (fansMembers.contains(req.getUserId().toString())){
            //存在 当前用户 把当前用户的从redis中移除
            Long remove = stringRedisTemplate.opsForSet().remove(followKey, req.getUserId().toString());
            log.info("移除的用户个数{}",remove);
            log.info("{}解除对{}的关注",req.getUserId(),req.getFollowUserId());
            //TODO 移除了之后怎么同步到数据库
            // 方法一:开一个线程去通知数据库把该数据同步
            // 方法二:这里采用Spring的发布于订阅来实现(采用)
            // 方法三:redis的发布订阅
            NotFollowEvent notFollowEvent = new NotFollowEvent(req);
            //发布事件
            SpringUtil.publishEvent(notFollowEvent);
        }else{
            //不存在 去数据库中查询当前用户是否关注过
            LambdaQueryWrapper<Fans> fansLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fansLambdaQueryWrapper.eq(Fans::getUserId,req.getUserId()).eq(Fans::getFollowUserId,req.getFollowUserId());
            Fans one = getOne(fansLambdaQueryWrapper);
            log.info("数据库中的数据{}",one);
            if (one!= null){
                //没查到表明 用户是第一次关注
                 one = BeanUtil.copyProperties(req, Fans.class);
                 one.setIsFollow(1);
            }else{
                //表明不是第一次关注 查看是直接对one进行已获操作
                one.setIsFollow(one.getIsFollow()^1);
            }
            //然后将当前的结果存入数据库 和存入redis中
//            save(one);
            //判断一下是否关注过
            if(one.getIsFollow() == 1){
                stringRedisTemplate.opsForSet().add(followKey,one.getUserId().toString());
            }else{
                save(one);
            }
        }
    }

    /**
     *  集合为我关注的人
     * @param req
     */

    public void followtest(FollowReq req) {
        //获取用户的id 拼接rediskey
        String followKey = USER_KEY + req.getUserId();
        //获取全部的粉丝
        Set<String> fansMembers = stringRedisTemplate.opsForSet().members(followKey);
        //判断是否存在当前的用户id
        if (fansMembers.contains(req.getFollowUserId().toString())){
            //存在 当前用户 把当前用户的从redis中移除
            Long remove = stringRedisTemplate.opsForSet().remove(followKey, req.getFollowUserId().toString());
            log.info("移除的用户个数{}",remove);
            log.info("{}解除对{}的关注",req.getFollowUserId(),req.getUserId());
            //TODO 移除了之后怎么同步到数据库
            // 方法一:开一个线程去通知数据库把该数据同步
            // 方法二:这里采用Spring的发布于订阅来实现(采用)
            // 方法三:redis的发布订阅
            NotFollowEvent notFollowEvent = new NotFollowEvent(req);
            //发布事件
            SpringUtil.publishEvent(notFollowEvent);
        }else{
            //不存在 去数据库中查询当前用户是否关注过
            LambdaQueryWrapper<Fans> fansLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fansLambdaQueryWrapper.eq(Fans::getUserId,req.getUserId()).eq(Fans::getFollowUserId,req.getFollowUserId());
            Fans one = getOne(fansLambdaQueryWrapper);
            log.info("数据库中的数据{}",one);
            if (one == null){
                //没查到表明 用户是第一次关注
                one = BeanUtil.copyProperties(req, Fans.class);
                one.setIsFollow(1);
            }else{
                //表明不是第一次关注 查看是直接对one进行已获操作
                one.setIsFollow(one.getIsFollow()^1);
            }
            //然后将当前的结果存入数据库或存入redis中
            //如果值为1就代表用户之前没有关注过 现在关注了就将用户存入redis后面回自动存入数据库中这里不需要管
            //如果值为0就代表之前关注了 要取消关注 直接去更新数据库即可
            if(one.getIsFollow() == 1){
                stringRedisTemplate.opsForSet().add(followKey,one.getFollowUserId().toString());
            }else{
                saveOrUpdate(one);
            }
        }
    }

    @Override
    public void executeAddFans(){
        ScanOptions scanOptions = ScanOptions.scanOptions().match("user:*").count(100).build();
        Cursor<String> cursor = stringRedisTemplate.scan(scanOptions);
        ThreadPoolExecutor executor = TaskExecutorConfig.getInstance().getFanExecutor();
        while (cursor.hasNext()) {
            String key = cursor.next();
            log.info("用户保存{}的粉丝", key);
            executor.execute(() -> addFansByUid(key));
        }
    }

    /**
     * 通过redis中的可以从redis集合中读取fans并保存
     * @param key
     */
    public void addFansByUid(String key){
        Set<String> members = stringRedisTemplate.opsForSet().members(key);
        members.stream().forEach(item -> {
            //先从数据库中查询一下是否有值
            Fans fans = this.getOne(Wrappers.<Fans>lambdaQuery().eq(Fans::getUserId, key.substring(5)).eq(Fans::getFollowUserId, Integer.parseInt(item)));
            if (fans == null){
                 fans = new Fans();
                fans.setUserId(Integer.parseInt(key.substring(5)));
                fans.setFollowUserId(Integer.parseInt(item));
            }
            fans.setIsFollow(1);
            log.info("当前保存的信息为:{}",fans);
            boolean save = this.saveOrUpdate(fans);
            log.info("粉丝{}关注{}成功",item,key);
        });
        stringRedisTemplate.delete(key);
        log.info("清除key成功");
    }
}
