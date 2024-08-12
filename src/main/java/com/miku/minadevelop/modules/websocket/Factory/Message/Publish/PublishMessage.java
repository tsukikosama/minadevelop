package com.miku.minadevelop.modules.websocket.Factory.Message.Publish;



import com.miku.minadevelop.modules.websocket.Factory.Minterface.IMessage;
import com.miku.minadevelop.modules.websocket.entity.Message;

import java.util.Map;

public class PublishMessage implements IMessage {

    @Override
    public void sendMessage(Message message, Map<String, String> extMap) {
        System.out.println("发送公共消息");
    }
}
