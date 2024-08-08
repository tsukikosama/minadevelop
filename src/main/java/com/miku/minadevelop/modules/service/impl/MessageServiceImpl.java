package com.miku.minadevelop.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.mapper.MessageMapper;
import com.miku.minadevelop.modules.response.MessageDetail;
import com.miku.minadevelop.modules.response.MessageEntityResp;
import com.miku.minadevelop.modules.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Override
    public Map<String,List<MessageEntityResp>> listUnreadMsg(Integer uid) {
        List<MessageEntityResp> messages = this.baseMapper.selectUnreadMessage(uid);
        //分组全部的消息
        Map<String, List<MessageEntityResp>> collect = messages.stream().collect(Collectors.groupingBy(MessageEntityResp::getChatId));
        return collect;
    }

    @Override
    public List<MessageEntityResp> listDetail(Long chatId) {
        List<MessageEntityResp> messages = this.baseMapper.selectMessageList(chatId);
        return messages;
    }

    @Override
    public List<MessageDetail> getDetailUnread(String chatId) {
        List<MessageDetail> message = this.baseMapper.selectUnreadMessageDetail(chatId);
        return message;
    }
}
