package com.miku.minadevelop.modules.response;

import com.miku.minadevelop.modules.entity.Message;
import lombok.Data;

@Data
public class MessageEntityResp extends Message {
    private String sendNickname;
    private String receiverNickname;
    private Long messageId;
}
