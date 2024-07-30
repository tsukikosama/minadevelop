package com.miku.minadevelop.modules.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import java.util.Set;

import static com.miku.minadevelop.modules.utils.RedisKey.FOLLOW_KEY;

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
        return result;
    }

    /**
     * 取消关注
     * @param req
     */
    public void notFollow(FollowReq req){
        Fans fans = BeanUtil.copyProperties(req, Fans.class);
        fans.setIsFollow(0);
        save(fans);
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
        String followKey = FOLLOW_KEY + req.getFollowUid();
        //获取全部的粉丝
        Set<String> fansMembers = stringRedisTemplate.opsForSet().members(followKey);
        //判断是否存在当前的用户id
        if (fansMembers.contains(req.getUserId())){
            //存在 当前用户 把当前用户的从redis中移除
            Long remove = stringRedisTemplate.opsForSet().remove(followKey, req.getUserId());
            log.info("{}解除对{}的关注",req.getUserId(),req.getFollowUid());
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
            fansLambdaQueryWrapper.eq(Fans::getUserId,req.getUserId()).eq(Fans::getFollowUserId,req.getFollowUid());
            Fans one = getOne(fansLambdaQueryWrapper);
            if (one!= null){
                //没查到表明 用户是第一次关注
                 one = BeanUtil.copyProperties(req, Fans.class);
                 one.setIsFollow(1);
            }else{
                //表明不是第一次关注 查看是直接对one进行已获操作
                one.setIsFollow(one.getIsFollow()^1);
            }
            //然后将当前的结果存入数据库 和存入redis中
            save(one);
            //判断一下是否关注过
            if(one.getIsFollow() == 1){
                stringRedisTemplate.opsForSet().add(followKey,one.getUserId().toString());
            }
        }
    }

    @Async("taskExecutor")
    @Override
    public void executeAddFans(){
        ScanOptions scanOptions = ScanOptions.scanOptions().match("FOLLOW:*").count(100).build();

        Cursor<String> cursor = stringRedisTemplate.scan(scanOptions);
        while (cursor.hasNext()) {
            String key = cursor.next();
            System.out.println("Found key: " + key);
        }
    }

}
