package com.miku.minadevelop.modules.event;

import org.springframework.context.ApplicationEvent;

/**
 * 使用了spring中的发布和监听事件
 */
public class NotFollowEvent extends ApplicationEvent {
    public NotFollowEvent(Object source) {
        super(source);

    }
}
