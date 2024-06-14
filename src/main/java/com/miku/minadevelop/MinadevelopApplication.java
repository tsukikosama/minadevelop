package com.miku.minadevelop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MinadevelopApplication {

    public static void main(String[] args) {
        log.info("项目启动了");
        SpringApplication.run(MinadevelopApplication.class, args);
    }

}
