package com.miku.minadevelop.modules.task;

import com.miku.minadevelop.modules.entity.Chat;
import com.miku.minadevelop.modules.service.IFansService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FansTask {

    private final IFansService fansService;

    /**
     * 每30分钟持久化一次粉丝
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void updateLastMessageId() {
        log.info("【定时任务】开始存储redis中的粉丝");
        fansService.executeAddFans();
    }
}
