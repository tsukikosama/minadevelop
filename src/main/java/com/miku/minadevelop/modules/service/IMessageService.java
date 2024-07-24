package com.miku.minadevelop.modules.service;

import com.miku.minadevelop.modules.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Map<Integer,List<Message>> listUnreadMsg(Integer uid);
}
