package com.miku.minadevelop.modules.enums;

public enum MessageStatusEnum {

    READ(1,"已读"),
    UN_READ(2,"未读")

    ;
    private int value;

    private String desc;

    MessageStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
