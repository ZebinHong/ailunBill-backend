package com.hunfeng.money.utils;

import com.alibaba.excel.util.StringUtils;
import com.hunfeng.money.annoation.DateTimeZone;

import java.lang.reflect.Field;

public class DateTimeZoneUtil {

    public static String getTimeZone(Field field, String defaultTimeZoneId) {
        DateTimeZone dateTimeZone = field.getAnnotation(DateTimeZone.class);
        if (dateTimeZone == null) {
            // 如果Field没有DateTimeZone注解，则使用全局的
            return defaultTimeZoneId;
        }
        String timeZoneId = dateTimeZone.value();
        if (StringUtils.isEmpty(timeZoneId)) {
            // 如果Field的DateTimeZone注解的值为空，则使用全局的
            return defaultTimeZoneId;
        }
        return timeZoneId;
    }
}

