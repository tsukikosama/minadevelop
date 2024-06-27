package com.miku.minadevelop.modules.service;

import com.miku.minadevelop.modules.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author miku
 * @since 2024-06-13
 */
public interface IUserService extends IService<User> {

    void resetUser(Integer uid);
}
