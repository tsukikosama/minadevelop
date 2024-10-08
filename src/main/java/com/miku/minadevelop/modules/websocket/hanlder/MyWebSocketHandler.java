package com.miku.minadevelop.modules.websocket.hanlder;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miku.minadevelop.common.exception.CustomException;
import com.miku.minadevelop.modules.entity.Chat;
import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.entity.User;
import com.miku.minadevelop.modules.request.RelationBody;
import com.miku.minadevelop.modules.service.IChatService;
import com.miku.minadevelop.modules.service.IMessageService;
import com.miku.minadevelop.modules.service.IUserService;
import com.miku.minadevelop.modules.utils.BeanUtils;
import com.miku.minadevelop.modules.utils.WeilaiUtils;
import com.miku.minadevelop.modules.websocket.Factory.MessageFactorty;
import com.miku.minadevelop.modules.websocket.enmus.MessageEnum;
import com.miku.minadevelop.modules.websocket.entity.MessageDetail;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.json.Json;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.miku.minadevelop.modules.websocket.enmus.MessageEnum.*;

@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {
    private IChatService chatService;
    public IMessageService messageService;

    private IUserService userService;
    public MyWebSocketHandler() {
        this.messageService = BeanUtils.getBean(IMessageService.class);
        this.chatService = BeanUtils.getBean(IChatService.class);
        this.userService = BeanUtils.getBean(IUserService.class);
    }

    /**
     * 在线用户的集合
     */
    public static final Map<String, WebSocketSession> userList = new HashMap<>();
    /**
     * 群集合
     */
    public static final Map<Long, List<WebSocketSession>> groupList = new HashMap<>();

    /**
     * 用户关注的集合
     * 使用消息队列更好
     * 消息推送可以采用发布订阅设计模式实现
     * 会占用比较大的内存空间   可以采用数据库持久化的方式  避免占用过多的空间
     */
    public static final Map<Long, List<Long>> followList = new HashMap<>();

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

        String id = parameters.get("userId");
//        System.out.println(Long.parseLong(id));
        session.getId();
        //判断用户是否连接过了 如果连接过了就把旧的连接替换掉
        if (userList.containsKey(id)) {
            userList.remove(id);
        }
        userList.put(id, session);
        log.info("当前连接用户数量为{}", userList.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)   {
//        log.info("当前的消息是{}",message);
//        String query = message.getPayload();
//        if (BeanUtil.isEmpty(message)){
//            throw new CustomException("数据异常 发送消息失败");
//        }
//        JsonObject parameters;
//        try {
//             parameters = getMessage(query);
//        }catch (IOException e){
//            throw new CustomException("数据为空 消息发送失败");
//        }
//        //通过消息类型来进行不同的处理
//        int msgType = parameters.get("msgType").getAsInt();
////        log.info("当前的message{},session:{},消息类型为:{}", message, session, sendMsgCommand(msgType));
////        Long receiverId = parameters.get("msgReceiver").getAsString();
////        Long sendId = parameters.get("msgSend").getAsLong();
//        //接收的人
//        String uid = parameters.get("uid").getAsString();
//        String send = "";
//        if (!parameters.get("chatId").isJsonNull() && !parameters.get("chatId").getAsString().isEmpty()){
//            Long chatId = parameters.get("chatId").getAsLong();
//            Chat chat = chatService.getById(chatId);
//            send = chat.getReceiverUid().equals(uid)?chat.getSendUid():chat.getSendUid();
//
//        }
        //获取消息对象
        com.miku.minadevelop.modules.websocket.entity.Message msgVal = getMessage(message);
        log.info("当前的消息对象为:{}",msgVal);
        //使用工厂模式实现
        MessageFactorty factorty = new MessageFactorty();
        factorty.sendMsg(msgVal.getMsgType(),msgVal);
        //通过chatId 获取chat对象
        //普通的方法
//        switch (sendMsgCommand(msgType)) {
//            case PERSON_MESSAGE:
//                sendPersonMsg(send, parameters,uid);
//                break;
//            case GROUP_MESSAGE:
//                log.info("给群发送消息");
//                break;
//            case HEART_MESSAGE:
//                sendHeart(uid);
//                break;
//            case PUBLISH_MESSAGE:
//                sendPublishMessage(uid, parameters);
//            default:
//                log.info("未知类型的消息");
//                throw new CustomException("消息类型异常");
//        }
    }

    /**
     * 解析消息的方法
     * @param message
     * @return
     */
    public com.miku.minadevelop.modules.websocket.entity.Message getMessage(TextMessage message){
        com.miku.minadevelop.modules.websocket.entity.Message msgValue = new com.miku.minadevelop.modules.websocket.entity.Message();
        String query = message.getPayload();
        if (BeanUtil.isEmpty(message)){
            throw new CustomException("数据异常 发送消息失败");
        }
        JsonObject parameters;
        try {
            parameters = getMessage(query);
        }catch (IOException e){
            throw new CustomException("数据为空 消息发送失败");
        }
        //通过消息类型来进行不同的处理
        Integer msgType = parameters.get("msgType").getAsInt();
        msgValue.setMsgType(msgType);
//        log.info("当前的message{},session:{},消息类型为:{}", message, session, sendMsgCommand(msgType));
//        Long receiverId = parameters.get("msgReceiver").getAsString();
//        Long sendId = parameters.get("msgSend").getAsLong();
        //接收的人
        String uid = parameters.get("uid").getAsString();
        msgValue.setReceiverUid(uid);
        String send = "";
        if (!parameters.get("chatId").isJsonNull() && !parameters.get("chatId").getAsString().isEmpty()){
            Long chatId = parameters.get("chatId").getAsLong();
            Chat chat = chatService.getById(chatId);
            send = chat.getReceiverUid().equals(uid)?chat.getSendUid():chat.getSendUid();
            msgValue.setSendUid(send);
        }
        String content = parameters.get("content").getAsString();
        msgValue.setContent(content);
        return msgValue;
    }

    public MessageEnum sendMsgCommand(int value) {
        for (MessageEnum item : MessageEnum.values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return UNDEFINED_MESSAGE;
    }

    /**
     * 连接断开触发的方法
     *
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
    public void sendHeart(String id) {
        log.info("当前用户的id为{}", id);
        WebSocketSession webSocketSession = userList.get(id);
        if (webSocketSession == null) {
            log.info("用户已经断开连接");
        }
        try {
            MessageDetail detail = BaseMessageDetail();
            webSocketSession.sendMessage(new TextMessage(new Gson().toJson(detail)));
        } catch (IOException e) {
            log.info("异常{}", e);
        }

    }

    /**
     * 发布动态消息
     */
    public void   sendPublishMessage(String sendId, JsonObject obj) {

    }

    /**
     *返回一个基本的messagedetail
     * 默认是用来发送心跳消息的 其他的消息 选哟设置其他的属性
     * @return
     */
    public MessageDetail BaseMessageDetail(){
        MessageDetail detail = new MessageDetail();
        detail.setReceiverUid("");
        detail.setSendUid("");
        detail.setMessageId("-1");
        detail.setChatId("-1");
        detail.setSendNickname("系统");
//        detail.setReceiverNickname();
        detail.setStatus("2");
        detail.setCreateTime(DateUtil.now());
        detail.setContent("pong");

        return detail;
    }

    /**
     * 点对点消息
     */
    public void sendPersonMsg(String msgSend, JsonObject obj,String receiverUid) {
        log.info("用户{}给用户{}发送{}类型的消息",msgSend,receiverUid,"个人消息");
        //获取消息内容
        String content = obj.get("content").getAsString();
        JsonElement chatId1 = obj.get("chatId");
        String chatId = null;
        if (chatId1 != null) {
            chatId = chatId1.getAsString();
        }
        String messageId = WeilaiUtils.generateId();
        //先判断用户是否是第一次发送消息  把自己当作接收者去数据库中查询一次
        // 如果没有 并且 chati为null 那么两个用户之间就没有发送过消息
        Chat findchat = new Chat();
        findchat.setSendUid(receiverUid);
        Chat one = chatService.getOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getSendUid, receiverUid).eq(Chat::getReceiverUid, msgSend));
        if (chatId == null && one == null) {
            //存入一个聊天关系集合 并且把用户聊天关系存入关系表中
            RelationBody req = new RelationBody();
            req.setSendUid(msgSend);
            req.setReceiverUid(receiverUid);
            chatId = chatService.getChatId(req);
        }
        if (one != null) {
            chatId = one.getId().toString();
        }
        //获取需要接收信息的用户通道
        WebSocketSession webSocketSession = userList.get(receiverUid);


        try {
            //通过chatid去查询出用户的昵称
            //通过uid 去查询用户的信息
            User send = userService.getById(msgSend);
            User receiver = userService.getById(receiverUid);
            MessageDetail messageDetail = getMessageDetail(messageId, msgSend
                    , receiverUid, content, send.getNickname(), receiver.getNickname(), chatId);
            webSocketSession.sendMessage(new TextMessage(new Gson().toJson(messageDetail)));
            log.info("发送成功{}",messageDetail);
        } catch (IOException e) {
            log.info("io异常");
        }
        //将消息存入数据库中
        Message message = new Message();
        message.setSendUid(msgSend);
        message.setReceiverUid(receiverUid);
        message.setContent(content);
        message.setStatus(2);
        message.setMessageId(messageId);
        message.setChatId(chatId);
//            System.out.println(message);
        messageService.save(message);

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




    public static MessageDetail getMessageDetail(String messageId, String sendUid, String receiverUid,
                                          String content, String sendNickname, String receiverNickname, String chatId) {
        MessageDetail detail = new MessageDetail();

        detail.setMessageId(messageId);
        detail.setSendUid(sendUid);
        detail.setReceiverUid(receiverUid);
        detail.setContent(content);
        detail.setCreateTime(DateUtil.now());
        detail.setStatus("1");
        detail.setSendNickname(sendNickname);
        detail.setReceiverNickname(receiverNickname);
        detail.setChatId(chatId);
        return detail;
    }


    private void sendMsg(){

    }
}
