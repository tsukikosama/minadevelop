package com.miku.minadevelop.modules.service;

import com.miku.minadevelop.modules.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miku.minadevelop.modules.response.MessageEntityResp;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
public interface IMessageService extends IService<Message> {

    Map<String,List<MessageEntityResp>> listUnreadMsg(Integer uid);

    List<MessageEntityResp> listDetail(Integer chatId);
}
