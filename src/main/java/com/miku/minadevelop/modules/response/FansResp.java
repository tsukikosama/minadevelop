package com.miku.minadevelop.modules.response;

import com.miku.minadevelop.modules.entity.Fans;
import lombok.Data;

@Data
public class FansResp extends Fans {
    private String nickname;
    private String avatar;
    private String followNickname;
    private String followAvatar;
    private Integer isBackFollow;
}
