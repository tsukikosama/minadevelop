package com.miku.minadevelop.modules.config;

import org.springframework.beans.factory.annotation.Value;

//@Component
//@ConfigurationProperties(prefix = "spring")
public class RedisHost {

    public static  String host;
    @Value("${spring.redis.host}")
    public void setHost(String host){
        RedisHost.host = host;
    }

}