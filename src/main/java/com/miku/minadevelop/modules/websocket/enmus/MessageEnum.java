package com.miku.minadevelop.modules.websocket.enmus;

public enum MessageEnum {

     PERSON_MESSAGE(1,"个人消息"),
     GROUP_MESSAGE(2,"群消息"),
     SYSTEM_MESSAGE(3,"系统消息")
    ;
    private Integer value;
    private String desc;

    MessageEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
