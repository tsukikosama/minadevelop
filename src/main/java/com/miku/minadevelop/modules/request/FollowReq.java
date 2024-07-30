package com.miku.minadevelop.modules.request;

import lombok.Data;

@Data
public class FollowReq {
    //用户
    private Long userId;
    //关注的用户id
    private Long followUid;
    //是否关注 0未关注 1关注
    private Integer isFollow;
}
