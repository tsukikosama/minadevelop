package com.miku.minadevelop.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@Slf4j
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_DATE = "createTime";
    private final static String UPDATE_DATE = "updateTime";
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert开始填充");
        LocalDateTime dateTime = LocalDateTime.now();
        strictInsertFill(metaObject, CREATE_DATE, LocalDateTime.class, dateTime);
        strictInsertFill(metaObject, UPDATE_DATE, LocalDateTime.class, dateTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updetakai开始填充");
        LocalDateTime dateTime = LocalDateTime.now();
        strictUpdateFill(metaObject, UPDATE_DATE, LocalDateTime.class, dateTime);
    }
}
