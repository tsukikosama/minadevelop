package com.miku.minadevelop.modules.security.config;

import com.miku.minadevelop.modules.security.UserRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 配置安全管理器
     * @param userRealm UserRealm
     * @return DefaultWebSecurityManager
     */
//    @Bean
//    public DefaultWebSecurityManager securityManager(UserRealm userRealm) {
//        //创建一个 DefaultWebSecurityManager
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        //设置自定义的 Realm，用于用户认证和授权
//        securityManager.setRealm(userRealm);
//        return securityManager;
//    }

    /**
     * 配置Shiro过滤器工厂
     * @param securityManager 安全管理器
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 注册安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /*
         * 设置登录页面的地址
         * 当用户访问认证资源的时候，如果用户没有登录，那么就会跳转到该属性指定的页面
         */
        shiroFilterFactoryBean.setLoginUrl("/login");


        // 配置权限拦截规则
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/login.html", "anon");  // 公共访问，无需认证
//        filterChainDefinitionMap.put("/logout", "logout");    // 登出
//        filterChainDefinitionMap.put("/**", "authc");         // 需要认证
        filterChainDefinitionMap.put("/api","authc");
        filterChainDefinitionMap.put("/**","anon");

        // 设置权限过滤链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        shiroFilterFactoryBean.setLoginUrl("/login");
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        return new DefaultWebSecurityManager();
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(WebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
