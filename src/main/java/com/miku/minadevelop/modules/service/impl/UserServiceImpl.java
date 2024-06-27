package com.miku.minadevelop.modules.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.miku.minadevelop.common.exception.CustomException;
import com.miku.minadevelop.modules.entity.User;
import com.miku.minadevelop.modules.mapper.UserMapper;
import com.miku.minadevelop.modules.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miku
 * @since 2024-06-13
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    /**
     * 提供用户的uid充值用户的密码
      * @param uid
     */
    @Override
    public void resetUser(Integer uid) {
        User user = this.getById(uid);
        if (BeanUtil.isEmpty(user)) {
            log.info("用户id{}不存在", uid);
            throw new CustomException("用户不存在");
        }
        user.setPassword(SecureUtil.md5("123456"));
        this.updateById(user);
    }
}
