package com.hunfeng.money.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.hunfeng.money.annoation.DateTimeZone;
import lombok.Data;

import java.util.Date;

@Data
public class ExcelBillData {
    @ExcelProperty(value = "交易时间", index = 0)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    @DateTimeZone("GMT+8") //自定义注解
    private Date recordTime;

//    @ExcelProperty(value = "交易类型", index = 1)
//    private String tagDetail;

    @ExcelProperty(value = "交易对方", index = 2)
    private String details;

    @ExcelProperty(value = "收/支", index = 4)
    private String typeDetail;

    @ExcelProperty(value = "金额（元）", index = 5)
    private Double money;
}
