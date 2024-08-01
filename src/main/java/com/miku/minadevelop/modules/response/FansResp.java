package com.miku.minadevelop.modules.response;

import com.miku.minadevelop.modules.entity.Fans;
import lombok.Data;

@Data
public class FansResp {
    private String userId;
    private String createTime;

    private String nickname;
    private String avatar;
//    private String followNickname;
//    private String followAvatar;
    private Integer isBackFollow;
    private Integer isFollow;
}
