package com.miku.minadevelop.modules.websocket.entity;

import lombok.Data;

@Data
public class MessageDetail {
    private String id;
    private String messageId;
    private Long sendUid;
    private Long receiverUid;
    private String content;
    private String createTime;
    private String status;
    private String sendNickname;
    private String receiverNickname;
    private String chatId;


}
