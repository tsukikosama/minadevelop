package com.miku.minadevelop.modules.pcrUtils;

import lombok.Data;

@Data
public class BaseResp {
    private int code ;
    private String message;
    private String request_id;
    private Object data;
}
