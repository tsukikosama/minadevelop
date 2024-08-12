package com.miku.minadevelop.modules.websocket.Factory.Message.Person;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.miku.minadevelop.modules.entity.Chat;


import com.miku.minadevelop.modules.entity.User;
import com.miku.minadevelop.modules.request.RelationBody;
import com.miku.minadevelop.modules.service.IChatService;
import com.miku.minadevelop.modules.service.IMessageService;
import com.miku.minadevelop.modules.service.IUserService;
import com.miku.minadevelop.modules.utils.BeanUtils;
import com.miku.minadevelop.modules.utils.WeilaiUtils;
import com.miku.minadevelop.modules.websocket.Factory.Minterface.IMessage;
import com.miku.minadevelop.modules.websocket.entity.Message;
import com.miku.minadevelop.modules.websocket.entity.MessageDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

import static com.miku.minadevelop.modules.websocket.hanlder.MyWebSocketHandler.getMessageDetail;
import static com.miku.minadevelop.modules.websocket.hanlder.MyWebSocketHandler.userList;

@Slf4j
public class PersonMessage implements IMessage {

    private IChatService chatService;

    private IUserService userService;

    public IMessageService messageService;
    public PersonMessage(){
        this.chatService = BeanUtils.getBean(IChatService.class);
        this.userService = BeanUtils.getBean(IUserService.class);
        this.messageService = BeanUtils.getBean(IMessageService.class);
    }

    @Override
    public void sendMessage(Message message, Map<String, String> extMap) {
        
        log.info("用户{}给用户{}发送{}", message.getSendUid(),message.getReceiverUid(),message.getContent());
        //生成消息id
        String messageId = WeilaiUtils.generateId();
        Chat findchat = new Chat();
        findchat.setSendUid(message.getReceiverUid());
        Chat one = chatService.getOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getSendUid, message.getReceiverUid()).eq(Chat::getReceiverUid,  message.getSendUid()));
        String chatId = null;
        if (message.getChatId() == null && one == null) {
            //存入一个聊天关系集合 并且把用户聊天关系存入关系表中
            RelationBody req = new RelationBody();
            req.setSendUid( message.getSendUid());
            req.setReceiverUid(message.getReceiverUid());
            chatId = chatService.getChatId(req);
        }
        if (one != null) {
            chatId = one.getId().toString();
        }
        //获取需要接收信息的用户通道
        WebSocketSession webSocketSession = userList.get(message.getReceiverUid());
        try {
            //通过chatid去查询出用户的昵称
            //通过uid 去查询用户的信息
            User send = userService.getById( message.getSendUid());
            User receiver = userService.getById(message.getReceiverUid());
//            MessageDetail messageDetail =  getMessageDetail(messageId,  message.getSendUid()
//                    , message.getReceiverUid(), message.getContent(), send.getNickname(), receiver.getNickname(), chatId);
            webSocketSession.sendMessage(new TextMessage(new Gson().toJson(message)));
            log.info("发送成功{}",message);
        } catch (IOException e) {
            log.info("io异常");
        }
        com.miku.minadevelop.modules.entity.Message msg = BeanUtil.copyProperties(message, com.miku.minadevelop.modules.entity.Message.class);
        log.info("转化的msg{}",msg);
        //将消息存入数据库中
        msg.setMessageId(WeilaiUtils.getMessageId());
        messageService.save(msg);
    }
}
