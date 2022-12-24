package com.hunfeng.money.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateStr = sdf.format(new Date());
        try {
            Date resDate = sdf.parse(dateStr);
            this.setFieldValByName("gmtCreate", resDate, metaObject);
            this.setFieldValByName("gmtModified", resDate, metaObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateStr = sdf.format(new Date());
        try {
            Date resDate = sdf.parse(dateStr);
            this.setFieldValByName("gmtModified", new Date(), metaObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
