package com.miku.minadevelop.modules.utils;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.miku.minadevelop.modules.entity.User;
import com.miku.minadevelop.modules.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

/**
 *用户的工具类
 */
@RequiredArgsConstructor
public class UserUtils {

    private final UserServiceImpl userService;
    /**
     * 获取用户id
     * @return
     */
    public Long getUserId(){
        //获取当前的用户信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Object loginId = tokenInfo.getLoginId();
        //通过用户id去保存商品
        return (Long) loginId;
    }

    /**
     * 获取用户的信息
     */
    public  User getUserInfo(){
       return userService.getById(getUserId());
    }

}
