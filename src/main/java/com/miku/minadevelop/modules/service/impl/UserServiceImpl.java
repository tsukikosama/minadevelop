package com.miku.minadevelop.modules.service.impl;

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
 * @since 2024-06-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
