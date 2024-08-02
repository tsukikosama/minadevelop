package com.miku.minadevelop.modules.response;

import com.miku.minadevelop.modules.entity.Message;
import lombok.Data;

import java.util.List;

@Data
public class MessageResp {
    private String chatId;
    private String ChatNickname;
    private String ChatUid;
    private List<MessageEntityResp> list;
}
