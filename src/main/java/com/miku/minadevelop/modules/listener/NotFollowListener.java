package com.miku.minadevelop.modules.listener;

import com.miku.minadevelop.modules.event.NotFollowEvent;
import com.miku.minadevelop.modules.request.FollowReq;
import com.miku.minadevelop.modules.service.IFansService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class NotFollowListener {

    private final IFansService fansService;
    @Async
    @EventListener(NotFollowEvent.class)
    public void notFollowEvent(NotFollowEvent event) {
        FollowReq source = (FollowReq) event.getSource();
        log.info("用户{}取消关注{}开始咯",source.getUserId(),source.getFollowUid());
        fansService.follow(source);
    }
}
