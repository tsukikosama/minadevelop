package com.miku.minadevelop.modules.response;

import lombok.Data;

@Data
public class MessageDetail {
    private String uid;
    private String avatar;
    private String nickname;
    private String content;
    private String number;
    private String chatId;
}
