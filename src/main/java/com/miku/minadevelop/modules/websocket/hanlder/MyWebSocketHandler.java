package com.miku.minadevelop.modules.websocket.hanlder;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.generator.IFill;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miku.minadevelop.common.exception.CustomException;
import com.miku.minadevelop.modules.entity.Chat;
import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.request.ChatRelationReq;
import com.miku.minadevelop.modules.service.IChatService;
import com.miku.minadevelop.modules.service.IMessageService;
import com.miku.minadevelop.modules.utils.BeanUtils;
import com.miku.minadevelop.modules.utils.WeilaiUtils;
import com.miku.minadevelop.modules.websocket.enmus.MessageEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

import static com.miku.minadevelop.modules.websocket.enmus.MessageEnum.*;


//@Component

@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {
    private IChatService chatService;
    public IMessageService messageService;

    public MyWebSocketHandler() {
        this.messageService = BeanUtils.getBean(IMessageService.class);
        this.chatService = BeanUtils.getBean(IChatService.class);
    }


    public static final Map<String, WebSocketSession> userList = new HashMap<>();

    /**
     * 开始连接调用的方法
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        Map<String, String> parameters = parseQueryParameters(query);

        String userId = parameters.get("userId");
        session.getId();
        //判断用户是否连接过了 如果连接过了就把旧的连接替换掉
        if (userList.containsKey(userId)) {
            userList.remove(userId);
            userList.put(userId, session);
        }
//        String id = session.getId();
//        System.out.println(id);
//        System.out.println("Connected: " + session.getId());
        userList.put(userId, session);
        log.info("当前连接用户数量为{}", userList.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        System.out.println(session.getUri());;
        log.info("当前的message{}", message);
        String query = message.getPayload();
        log.info("当前的session为：{}", session);

        JsonObject parameters = getMessage(query);
        String id = session.getId();
        //通过消息类型来进行不同的处理
        int msgType = parameters.get("msgType").getAsInt();
        switch (sendMsgCommand(msgType)) {
            case PERSON_MESSAGE:
                log.info("给用户发送消息");
                sendPersonMsg(id, parameters);
                break;
            case GROUP_MESSAGE:
                log.info("给群发送消息");
                ;
                break;
            case HEART_MESSAGE:
                sendHeart(id);
                break;
//            case 4:
//                sendSystemMsg(new TextMessage());
            default:
                log.info("未知类型的消息");
                throw new CustomException("消息类型异常");
        }


    }


    public MessageEnum sendMsgCommand(int value){
        for (MessageEnum item : MessageEnum.values()){
            if (item.getValue() == value){
                return item;
            }
        }
        return UNDEFINED_MESSAGE;
    }


    /**
     * 连接断开触发的方法
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("用户断开连接");
        //将session从集合中移除
        userList.remove(session.getId());
//        System.out.println("Disconnected: " + session.getId());
    }

    /**
     * 处理消息变成消息对象类型
     *
     * @param payload
     * @return
     */
    public JsonObject getMessage(String payload) throws UnsupportedEncodingException {
        log.info("当前的数据内容为:{}", payload);
        // 假设初始化消息格式为 {"type":"init", "userId":"user123", "roomId":"room456"}
//        System.out.println(payload);
        JsonObject jsonMessage = new JsonParser().parse(payload).getAsJsonObject();
        return jsonMessage;
    }


    /**
     * 群消息
     */
    public void sendSystemMsg(JsonObject jsonObject) {

    }

    public void sendHeart(String id)  {
        WebSocketSession webSocketSession = userList.get(id);
        if (webSocketSession == null){
            log.info("用户已经断开连接");
        }
        try {
            webSocketSession.sendMessage(new TextMessage(("pong".getBytes(StandardCharsets.UTF_8))));
        }catch (IOException e){
            log.info("异常{}",e);
        }

    }

    /**
     *
     */
    public void sendPersonMsg(String id, JsonObject obj) {
        String receiverUid = obj.get("msgReceiver").getAsString();
        System.out.println(obj.toString());
        String msgSend = obj.get("msgSend").getAsString();
        //获取消息内容
        String content = obj.get("msgContent").getAsString();
        JsonElement chatId1 = obj.get("chatId");
        Long chatId = null;
        if (chatId1 != null){
            chatId = chatId1.getAsLong();
        }
        Long messageId = WeilaiUtils.generateId();
        //先判断用户是否是第一次发送消息  把自己当作接收者去数据库中查询一次
        // 如果没有 并且 chati为null 那么两个用户之间就没有发送过消息
        Chat findchat = new Chat();
        findchat.setSendUid(Long.getLong(receiverUid));
        Chat one = chatService.getOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getSendUid, Integer.parseInt(receiverUid)).eq(Chat::getReceiverUid, Integer.parseInt((msgSend))));
        if (chatId == null && one == null) {
            //存入一个聊天关系集合 并且把用户聊天关系存入关系表中
            ChatRelationReq req = new ChatRelationReq();
            req.setSendId(Long.getLong(msgSend));
            req.setReceiverId(Long.getLong(receiverUid));
            chatId = chatService.createRelation(req);
        }
        if (one != null){
            chatId = one.getId();
        }
        //获取到当前用户的通道
        WebSocketSession webSocketSession = userList.get(id);
        //如果用户不在线就把消息存入数据库中
        if (webSocketSession == null) {
            Message message = new Message();
            message.setSendUid(Long.getLong(msgSend));
            message.setSendUid(Long.getLong(receiverUid));
            message.setContent(content);
            message.setStatus(2);
            message.setMessageId(messageId);
            message.setChatId(chatId);
            messageService.save(message);
            return;
        }

        try {
            webSocketSession.sendMessage(new TextMessage(content.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            log.info("io异常");
        }


    }

    private Map<String, String> parseQueryParameters(String query) {
        Map<String, String> parameters = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
            String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
            parameters.put(key, value);
        }
        return parameters;
    }

}
