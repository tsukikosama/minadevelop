package com.miku.minadevelop.service.impl;

import com.miku.minadevelop.entity.User;
import com.miku.minadevelop.mapper.UserMapper;
import com.miku.minadevelop.service.IUserService;
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
