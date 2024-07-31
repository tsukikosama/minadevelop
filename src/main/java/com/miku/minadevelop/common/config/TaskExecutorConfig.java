package com.miku.minadevelop.common.config;


import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;


@Getter
@Slf4j
public class TaskExecutorConfig {

    private static final TaskExecutorConfig INSTANCE = new TaskExecutorConfig();

    public static TaskExecutorConfig getInstance() {
        return INSTANCE;
    }

    private final ThreadPoolExecutor fanExecutor;


//    private final ThreadPoolExecutor fakerOrderExecutor;


    private TaskExecutorConfig() {
        log.info("初始化线程池设置。。。。");
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        fanExecutor = new ThreadPoolExecutor(
                1500,
                1500,
                keepAliveTime,
                unit,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNamePrefix("fans").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
