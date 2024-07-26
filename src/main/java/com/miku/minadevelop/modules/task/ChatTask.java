package com.miku.minadevelop.modules.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.modules.entity.Chat;
import com.miku.minadevelop.modules.request.ChatRelationReq;
import com.miku.minadevelop.modules.service.IChatService;
import com.miku.minadevelop.modules.service.IMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatTask {

    private final IChatService chatService;

    private final IMessageService messageService;
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateLastMessageId() {
        log.info("【定时任务】更新用户的最后一条消息");
        List<Chat> list = chatService.list();
//        boolean stop = true;
        for(Chat item : list){
            //通过chatid 去查询最后一条消息的时间
            chatService.updateLastMessageId(item.getId());
        }
        log.info("【定时任务】更新最后一条消息id完成");
    }
}
