package com.miku.minadevelop.modules.websocket.Factory;

import com.miku.minadevelop.common.exception.CustomException;

import com.miku.minadevelop.modules.websocket.Factory.Message.Heart.HeartMessage;
import com.miku.minadevelop.modules.websocket.Factory.Message.Person.PersonMessage;
import com.miku.minadevelop.modules.websocket.Factory.Message.Publish.PublishMessage;
import com.miku.minadevelop.modules.websocket.Factory.Minterface.IMessage;
import com.miku.minadevelop.modules.websocket.enmus.MessageEnum;

import com.miku.minadevelop.modules.websocket.entity.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

import static com.miku.minadevelop.modules.websocket.enmus.MessageEnum.UNDEFINED_MESSAGE;

/**
 * 消息工厂
 */
@Slf4j
public class MessageFactorty {
    public IMessage sendMsg(int msgType, Message message){
        switch (sendMsgCommand(msgType)) {
            case PERSON_MESSAGE:
                 new PersonMessage().sendMessage(message,null);
                break;
            case GROUP_MESSAGE:
                log.info("给群发送消息");
                break;
            case HEART_MESSAGE:
                new HeartMessage().sendMessage(message,null);
                break;
            case PUBLISH_MESSAGE:
                 new PublishMessage().sendMessage(message,null);
                break;
            default:
                log.info("未知类型的消息");
                throw new CustomException("消息类型异常");
        }
        return null;
    }

    public MessageEnum sendMsgCommand(int value) {
        for (MessageEnum item : MessageEnum.values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return UNDEFINED_MESSAGE;
    }
}
