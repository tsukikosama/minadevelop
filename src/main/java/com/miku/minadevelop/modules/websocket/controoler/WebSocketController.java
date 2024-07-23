package com.miku.minadevelop.modules.websocket.controoler;

import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.websocket.entity.Message;
import org.springframework.stereotype.Component;

import static com.miku.minadevelop.modules.websocket.hanlder.MyWebSocketHandler.userList;


@Component
public class WebSocketController {

    public Result sendMsg(Message message){
        System.out.println(message);
        int size = userList.size();
        System.out.println(size);
        return new Result();
    }
}
