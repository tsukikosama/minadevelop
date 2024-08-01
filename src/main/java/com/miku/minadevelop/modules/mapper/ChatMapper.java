package com.miku.minadevelop.modules.mapper;

import com.miku.minadevelop.modules.entity.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miku.minadevelop.modules.response.ChatRelationResp;
import com.miku.minadevelop.modules.response.MessageEntityResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */

public interface ChatMapper extends BaseMapper<Chat> {

    List<MessageEntityResp> findUserMessage(String uid);

    void updetaLastMessageId(Long chatId);

    String getChatId(@Param("sendUid") Long sendUid, @Param("receiverUid") Long receiverUid);

    ChatRelationResp getRelationByChatId(@Param("chatId") Long chatId);

}
