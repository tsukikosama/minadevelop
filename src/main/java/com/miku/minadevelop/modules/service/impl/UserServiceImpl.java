package com.miku.minadevelop.modules.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.miku.minadevelop.modules.entity.User;
import com.miku.minadevelop.modules.mapper.UserMapper;
import com.miku.minadevelop.modules.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void resetUser(Integer uid) {
        User u = new User();
        u.setUserId(uid);
        u.setPassword(SecureUtil.md5("123456"));
        this.save(u);
    }
}
