package com.miku.minadevelop.modules.websocket.enmus;


public enum MessageEnum {

    PERSON_MESSAGE(1, "个人消息"),
    GROUP_MESSAGE(2, "群消息"),
    HEART_MESSAGE(-99, "心跳检测"),

    PUBLISH_MESSAGE(3, "动态消息"),
    UNDEFINED_MESSAGE(500, "未定义");
    private int value;
    private String desc;

    MessageEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue() {
        return this.value;
    }


    public String getDesc() {
        return this.desc;
    }
}
