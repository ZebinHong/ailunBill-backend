package com.hunfeng.money.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hunfeng.money.config.DateTimeZoneStringConverter;
import com.hunfeng.money.entity.AlipayExcelBillData;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.entity.ExcelBillData;
import com.hunfeng.money.listener.AlipayExcelBillDataListener;
import com.hunfeng.money.listener.ExcelBillDataListener;
import com.hunfeng.money.mapper.BillMapper;
import com.hunfeng.money.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Service
//设置多例，防止线程安全问题
@Scope("prototype")
public class ExcelServiceImpl implements ExcelService {
    private List<Bill> excelResList = new ArrayList<>();
    @Autowired
    private BillMapper billMapper;
    @Override
    public void addExcelResList(List<Bill> bills){
        excelResList.addAll(bills);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Bill> batchImport(InputStream inputStream, Integer userId) {
        excelResList.clear();
        EasyExcel.read(inputStream, ExcelBillData.class, new ExcelBillDataListener(billMapper, this, userId))
                .registerConverter(new DateTimeZoneStringConverter("GMT+8"))
                .excelType(ExcelTypeEnum.XLSX)
                .headRowNumber(17) //从第18行开始读取数据
                .sheet()
                .doRead();
        return excelResList;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Bill> alipayBatchImport(InputStream inputStream, Integer userId) {
        excelResList.clear();
        EasyExcel.read(inputStream, AlipayExcelBillData.class, new AlipayExcelBillDataListener(billMapper, this, userId))
                .registerConverter(new DateTimeZoneStringConverter("GMT+8"))
                .excelType(ExcelTypeEnum.XLSX)
                .headRowNumber(25)
                .sheet()
                .doRead();
        return excelResList;
    }
}
