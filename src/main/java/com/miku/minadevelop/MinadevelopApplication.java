package com.miku.minadevelop;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//配置包扫描
@Slf4j
public class MinadevelopApplication {

    public static void main(String[] args) {
        log.info("项目启动了");
        SpringApplication.run(MinadevelopApplication.class, args);
    }

}
