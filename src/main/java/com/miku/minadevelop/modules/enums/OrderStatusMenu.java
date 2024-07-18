package com.miku.minadevelop.modules.enums;

public enum OrderStatusMenu {
    UN_PAY(1),
    PROCESS(2),
    PAYED(3),
    REFUND(4);

    private int value;

    OrderStatusMenu(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
