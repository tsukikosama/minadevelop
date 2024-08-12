package com.miku.minadevelop.modules.websocket.Factory.Minterface;



import com.miku.minadevelop.modules.websocket.entity.Message;

import java.util.Map;

/**
 * 消息工厂接口
 */
public interface IMessage {
    /**
     *
     * @param message 消息对象
     * @param extMap 预留的字段
     */
    public void sendMessage(Message message, Map<String,String>extMap);
}
