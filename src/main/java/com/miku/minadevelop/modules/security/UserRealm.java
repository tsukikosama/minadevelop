package com.miku.minadevelop.modules.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.modules.entity.User;
import com.miku.minadevelop.modules.service.IUserService;
import com.miku.minadevelop.modules.service.impl.UserServiceImpl;
import com.miku.minadevelop.modules.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRealm extends AuthorizingRealm {


    private final  IUserService userService;

//    public UserRealm() {
//        this.userService = BeanUtils.getBean(IUserService.class);
//    }
    /**
     * 认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 授权
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // Implement user authentication logic here
        String account = (String) authenticationToken.getPrincipal();
        //从数据库中获取用户信息
        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
        // For demo, we just accept any username with password "password"
        if (one != null) {
            return new SimpleAuthenticationInfo(account, one.getPassword(), getName());
        }
        return null;
    }
}
