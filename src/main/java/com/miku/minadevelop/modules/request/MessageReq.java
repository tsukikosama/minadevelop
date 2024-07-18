package com.miku.minadevelop.modules.request;

import lombok.Data;

@Data
public class MessageReq {
    private String sendUid;
    private String receiveUid;
    private String content;
}
