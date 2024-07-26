package com.miku.minadevelop.modules.service;

import com.miku.minadevelop.modules.entity.Chat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miku.minadevelop.modules.request.ChatRelationReq;
import com.miku.minadevelop.modules.response.MessageEntityResp;
import com.miku.minadevelop.modules.response.MessageResp;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
public interface IChatService extends IService<Chat> {

    List<MessageResp> listUserChat(String uid);

    Long createRelation(ChatRelationReq req);

    void updateLastMessageId(Long chatId);
}
