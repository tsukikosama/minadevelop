package com.miku.minadevelop.modules.request;

import lombok.Data;

@Data
public class ChatRelationReq {
    private Integer chatId;
    private Long sendId;
    private Long receiverId;
    private String content;
}
