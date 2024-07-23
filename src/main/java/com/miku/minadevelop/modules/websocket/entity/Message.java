package com.miku.minadevelop.modules.websocket.entity;

import lombok.Data;

@Data
public class Message {
    /**
     * 发送人的id
     */
    private String msgSend;
    /**
     * 接收人的id
     */
    private String msgReceiver;
    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息的类型
     */
    private String msgType;
}
