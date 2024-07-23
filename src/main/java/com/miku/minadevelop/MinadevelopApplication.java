package com.miku.minadevelop;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//配置包扫描
@Slf4j
@RequiredArgsConstructor
public class MinadevelopApplication {



    public static void main(String[] args)  {
        log.info("项目启动了");

        SpringApplication.run(MinadevelopApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        NettyServer server = new NettyServer(8080); // 使用8080端口作为Netty服务端的监听端口
//        server.start(); // 启动Netty服务端
//    }

}
