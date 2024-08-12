package com.miku.minadevelop.modules.websocket.Factory.Message.Heart;

import com.google.gson.Gson;
import com.miku.minadevelop.common.exception.CustomException;

import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.websocket.Factory.Minterface.IMessage;
import com.miku.minadevelop.modules.websocket.entity.MessageDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


import java.io.IOException;
import java.util.Map;

import static com.miku.minadevelop.modules.websocket.hanlder.MyWebSocketHandler.userList;

@Slf4j
public class HeartMessage implements IMessage {



    @Override
    public void sendMessage(com.miku.minadevelop.modules.websocket.entity.Message message, Map<String, String> extMap) {
        log.info("当前用户的id为{}", message.getSendUid());
        WebSocketSession webSocketSession = userList.get(message.getSendUid());
        if (webSocketSession == null) {
            log.info("用户已经断开连接");
        }
        try {
//            MessageDetail detail = BaseMessageDetail();
            webSocketSession.sendMessage(new TextMessage(new Gson().toJson(message)));
        } catch (IOException e) {
            log.info("异常{}", e);
            throw new CustomException("消息发送失败");
        }
    }
}
