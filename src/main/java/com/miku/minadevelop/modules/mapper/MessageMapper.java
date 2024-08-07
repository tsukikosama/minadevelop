package com.miku.minadevelop.modules.mapper;

import com.miku.minadevelop.modules.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miku.minadevelop.modules.response.MessageEntityResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    List<MessageEntityResp> selectMessage(Integer uid);

    List<MessageEntityResp> selectMessageList(Integer chatId);

    List<MessageEntityResp> selectUnreadMessage(Integer uid);
}
