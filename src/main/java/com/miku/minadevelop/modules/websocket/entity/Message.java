package com.miku.minadevelop.modules.websocket.entity;

import lombok.Data;

/**
 *
 */
@Data
public class Message {
    /**
     * 发送人的id
     */
    private String sendUid;
    /**
     * 接收人的id
     */
    private String receiverUid;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息的类型
     */
    private Integer msgType;

    private String chatId;
}
