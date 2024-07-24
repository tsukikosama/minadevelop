package com.miku.minadevelop.modules.websocket.hanlder;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miku.minadevelop.common.exception.CustomException;
import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.service.IMessageService;
import com.miku.minadevelop.modules.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
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


//@Component

@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private IMessageService iMessageService;
    public MyWebSocketHandler(){

        this.iMessageService = BeanUtils.getBean(IMessageService.class);
    }
    public  IMessageService messageService;

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
//        String id = session.getId();
//        System.out.println(id);
//        System.out.println("Connected: " + session.getId());
        userList.put(userId,session);
        log.info("当前连接用户数量为{}",userList.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        System.out.println(session.getUri());;
        log.info("当前的message{}",message);
        String query = message.getPayload();
        log.info("当前的session为：{}",session);

//        System.out.println(session);
//        System.out.println(message);
//        System.out.println(query);
        JsonObject parameters = getMessage(query);
        String id = session.getId();
        //通过消息类型来进行不同的处理
        int msgType = parameters.get("msgType").getAsInt();
        switch (msgType) {
            case 1:
               log.info("给用户发送消息");
               sendPersonMsg(id,parameters);
               break;
            case 2:
                log.info("给群发送消息");
                ;
                break;
            case 3:
                log.info("系统消息");
                break;
//            case 4:
//                sendSystemMsg(new TextMessage());
            default:
                log.info("未知类型的消息");
                throw new CustomException("消息类型异常");
        }


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
        log.info("当前的数据内容为:{}",payload);
        // 假设初始化消息格式为 {"type":"init", "userId":"user123", "roomId":"room456"}
//        System.out.println(payload);
        JsonObject jsonMessage = new JsonParser().parse(payload).getAsJsonObject();
        return jsonMessage;

    }


    /**
     * 群消息
     */
    public void sendSystemMsg(JsonObject jsonObject){

    }

    public void sendHeart(String msg){

    }
    /**
     *
     */
    public void sendPersonMsg(String id,JsonObject obj){

        String receiverUid = obj.get("msgReceiver").getAsString();
        System.out.println(obj.toString());
        String msgSend = obj.get("msgSend").getAsString();
        //获取消息内容
        String content = obj.get("msgContent").getAsString();
        //获取到当前用户的通道
        WebSocketSession webSocketSession = userList.get(id);
        //如果用户不在线就把消息存入数据库中
        if (webSocketSession == null){
            Message message = new Message();
            message.setSendId(Integer.parseInt(msgSend));
            message.setReceiverId(Integer.parseInt(receiverUid));
            message.setContent(content);
            message.setStatus(2);
            messageService.save(message);
            return;
        }

        try {
            webSocketSession.sendMessage(new TextMessage(content.getBytes(StandardCharsets.UTF_8)));
        }catch (IOException e){
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
