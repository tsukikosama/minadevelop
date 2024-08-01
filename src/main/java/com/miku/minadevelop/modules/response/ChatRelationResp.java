package com.miku.minadevelop.modules.response;

import com.miku.minadevelop.modules.entity.Chat;
import lombok.Data;

@Data
public class ChatRelationResp extends Chat {
    private String nickname;
    private String avatar;
    private String uid;
}
