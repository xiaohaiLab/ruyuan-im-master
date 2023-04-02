package com.ruyuan2020.im.common.web.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruyuan2020.im.common.core.util.DateTimeUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = DateTimeUtils.currentDateTime();
        this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "gmtModified", LocalDateTime.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "gmtModified", LocalDateTime.class, DateTimeUtils.currentDateTime());
    }
}
