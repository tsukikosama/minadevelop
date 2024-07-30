package com.miku.minadevelop;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
//开启定时任务
@EnableScheduling
//开启支持异步
@EnableAsync
public class MinadevelopApplication {

    public static void main(String[] args)  {
        log.info("项目启动了");
        SpringApplication.run(MinadevelopApplication.class, args);
    }

}
