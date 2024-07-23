package com.miku.minadevelop.modules.response;

import lombok.Data;

@Data
public class UserResponse {
    private String userId;
    private String nickname;
    private String account;
    private String avatar;
    private String email;
    private String createTime;
    private String isValid;
}
