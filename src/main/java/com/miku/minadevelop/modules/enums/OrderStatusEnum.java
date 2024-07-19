package com.miku.minadevelop.modules.enums;

public enum OrderStatusEnum {
    UN_PAY(1,"未支付"),
    PROCESS(2,"支付中"),
    PAYED(3,"支付完成"),
    REFUND(4,"退款");

    private int value;
    private String desc;

    OrderStatusEnum(int value,String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
