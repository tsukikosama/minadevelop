package com.miku.minadevelop.modules.service.impl;

import com.miku.minadevelop.modules.entity.Chat;
import com.miku.minadevelop.modules.mapper.ChatMapper;
import com.miku.minadevelop.modules.service.IChatService;
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
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {

}
