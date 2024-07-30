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
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateLastMessageId() {

    }
}
