package com.miku.minadevelop.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.mapper.MessageMapper;
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
    public Map<Integer,List<Message>> listUnreadMsg(Integer uid) {
        List<Message> messages = this.baseMapper.selectMessage(uid);
        //分组全部的消息
        Map<Integer, List<Message>> collect = messages.stream().collect(Collectors.groupingBy(Message::getSendId));
        return collect;
    }
}
