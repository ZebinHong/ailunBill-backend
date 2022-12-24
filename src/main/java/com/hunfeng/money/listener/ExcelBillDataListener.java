package com.hunfeng.money.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.entity.ExcelBillData;
import com.hunfeng.money.mapper.BillMapper;
import com.hunfeng.money.service.impl.BillServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor //全参
@NoArgsConstructor //无参
public class ExcelBillDataListener extends AnalysisEventListener<ExcelBillData> {

    private BillMapper billMapper;

    private Integer userId;
    @Override
    public void invoke(ExcelBillData data, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据:"+ data);
        Double money = data.getMoney();
        String details = data.getDetails();
        Date recordTime = data.getRecordTime();
        String typeDetail = data.getTypeDetail();
        Integer type = 1;
        if (typeDetail.equals("收入")){
            type = 0;
        }
        Bill bill = new Bill().setMoney(money)
                .setDetails(details)
                .setTagId(9) //默认为excel导入
                .setType(type)
                .setRecordTime(recordTime)
                .setUserId(userId);
        billMapper.insert(bill);
        BillServiceImpl.addExcelResList(bill);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("导入完成");
    }
}
