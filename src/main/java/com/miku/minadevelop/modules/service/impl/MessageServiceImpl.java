package com.miku.minadevelop.modules.service.impl;

import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.mapper.MessageMapper;
import com.miku.minadevelop.modules.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
