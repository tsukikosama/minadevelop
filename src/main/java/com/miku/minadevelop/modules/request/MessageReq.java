package com.miku.minadevelop.modules.request;

import lombok.Data;

@Data
public class MessageReq {
    private String msgSend;
    private String msgReceiver;
    private String msgContent;
}
