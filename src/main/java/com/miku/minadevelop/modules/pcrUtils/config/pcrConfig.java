package com.miku.minadevelop.modules.pcrUtils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "pcr")
public class pcrConfig {
    private String name;
    private String ts;
    private String nonce;
    private String appkey;
    private String sign;
    private String Cookie;
}
