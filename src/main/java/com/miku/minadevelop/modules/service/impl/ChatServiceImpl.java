package com.miku.minadevelop.modules.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.miku.minadevelop.modules.entity.Chat;
import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.mapper.ChatMapper;
import com.miku.minadevelop.modules.request.ChatRelationReq;
import com.miku.minadevelop.modules.request.RelationBody;
import com.miku.minadevelop.modules.response.ChatRelationResp;
import com.miku.minadevelop.modules.response.MessageEntityResp;
import com.miku.minadevelop.modules.response.MessageResp;
import com.miku.minadevelop.modules.service.IChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miku.minadevelop.modules.utils.WeilaiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@Service
@Slf4j
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {

    /**
     * 通过聊天信息创建用户的聊天关系集合
     * @param message
     */
    public void ChatRelationCreate(Message message){
        //先判断是否拥有该关系
        String chatId = message.getChatId();
        Chat chatRelation = getById(chatId);
        if (!BeanUtil.isEmpty(chatRelation)){
            return;
        }
        Chat chat = new Chat();
        chat.setReceiverUid(message.getReceiverUid());
        chat.setSendUid(message.getSendUid());
        chat.setLastMessageId(message.getId());
    }

    @Override
    public List<MessageResp> listUserChat(String uid) {
        List<MessageEntityResp> list = this.baseMapper.findUserMessage(uid);
        System.out.println(list);
        Map<String, List<MessageEntityResp>> collect = list.stream().collect(Collectors.groupingBy(MessageEntityResp::getChatId));
        List<MessageResp> messageList = new ArrayList<>();
        for(Map.Entry<String,List<MessageEntityResp>> item : collect.entrySet()){
            MessageResp msg = new MessageResp();
            msg.setChatId(item.getKey().toString());
//            msg.setChatNickname();
            MessageEntityResp messageEntityResp = item.getValue().get(0);
            System.out.println(messageEntityResp.getSendUid());
            //这边使用用户的uid去匹配 如果相同就取发送者的 不相同就取接收者的
            if (messageEntityResp.getSendUid().toString().equals(uid)){
                msg.setChatNickname(messageEntityResp.getReceiverNickname());
                msg.setChatUid(String.valueOf(messageEntityResp.getReceiverUid()));
            }else{
                msg.setChatNickname(messageEntityResp.getSendNickname());
                msg.setChatUid(String.valueOf(messageEntityResp.getSendUid()));
            }
            msg.setList(item.getValue());
            messageList.add(msg);
        }
        return messageList;
    }

    /**
     *查询两个用户的chatId
     * @param req
     * @return
     */
    @Override
    public String getChatId(RelationBody req){
        //先从数据库中查找是否有用户的聊天关系
        String chatId = baseMapper.getChatId(req.getSendUid(),req.getReceiverUid());
        log.info("chatId{}",chatId);

        if (chatId == null){
            Chat chat = new Chat();
            chat.setReceiverUid(String.valueOf(req.getReceiverUid()));
            chat.setSendUid(String.valueOf(req.getSendUid()));
            chatId = WeilaiUtils.generateId();
            chat.setId(chatId);
            this.save(chat);
        }
        log.info("chatId{}",chatId);
        return chatId;
    }

    @Override
    public void updateLastMessageId(String chatId) {
        this.baseMapper.updetaLastMessageId(chatId);
    }

    @Override
    public ChatRelationResp getRelationByChatId(String chatId,String uid) {

        return baseMapper.getRelationByChatId(chatId,uid);
    }

    @Override
    public List<String> listChatRelationAll(String chatId) {

        return baseMapper.getChatRelationAll(chatId);
    }
}
