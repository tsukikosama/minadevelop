package com.miku.minadevelop.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {


    private final static String CREATE_DATE = "createTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime dateTime = LocalDateTime.now();
        strictInsertFill(metaObject, CREATE_DATE, LocalDateTime.class, dateTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime dateTime = LocalDateTime.now();
        strictInsertFill(metaObject, CREATE_DATE, LocalDateTime.class, dateTime);
    }
}
