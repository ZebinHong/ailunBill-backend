package com.hunfeng.money.config;


import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.hunfeng.money.utils.DateTimeZoneUtil;
import com.hunfeng.money.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateTimeZoneStringConverter extends DateStringConverter {
    private final String globalTimeZoneId;

    public DateTimeZoneStringConverter() {
        super();
        this.globalTimeZoneId = null;
    }

    public DateTimeZoneStringConverter(String globalTimeZoneId) {
        super();
        this.globalTimeZoneId = globalTimeZoneId;
    }

    @Override
    public Date convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws ParseException {
        String timeZoneId = getTimeZoneId(contentProperty);
        String timeFormat = getTimeFormat(contentProperty);

        // System.out.println(String.format("%s: %s: %s", cellData.getStringValue(), timeFormat, timeZoneId));
        Date date = DateUtils.parseDate(cellData.getStringValue(), timeFormat , timeZoneId);
        return date;
    }
    private String getTimeZoneId(ExcelContentProperty contentProperty) {
        if (contentProperty == null) {
            return null;
        }
        return DateTimeZoneUtil.getTimeZone(contentProperty.getField(), globalTimeZoneId);
    }

    private String getTimeFormat(ExcelContentProperty contentProperty) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return null;
        }
        return contentProperty.getDateTimeFormatProperty().getFormat();
    }
}

