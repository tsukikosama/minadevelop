package com.miku.minadevelop.modules.service;

import com.miku.minadevelop.modules.entity.Chat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miku.minadevelop.modules.request.ChatRelationReq;
import com.miku.minadevelop.modules.request.RelationBody;
import com.miku.minadevelop.modules.response.ChatRelationResp;
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

    String getChatId(RelationBody req);

    void updateLastMessageId(Long chatId);

    ChatRelationResp getRelationByChatId(Long chatId);

}
