package com.hunfeng.money.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.entity.ExcelBillData;
import com.hunfeng.money.mapper.BillMapper;
import com.hunfeng.money.myenum.TagEnum;
import com.hunfeng.money.service.impl.BillServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor //全参
@NoArgsConstructor //无参
public class ExcelBillDataListener extends AnalysisEventListener<ExcelBillData> {
    private static final int BATCH_COUNT = 50;

    private List<Bill> cachedDataList = new ArrayList<>();
    private BillMapper billMapper;

    private Integer userId;
    public ExcelBillDataListener(BillMapper billMapper, Integer userId){
        this.billMapper = billMapper;
        this.userId = userId;
    }
    @Override
    public void invoke(ExcelBillData data, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据:"+ data);
        Double money = data.getMoney();
        String details = data.getDetails();
        Date recordTime = data.getRecordTime();
        String typeDetail = data.getTypeDetail();
        if (money == null && details == null && recordTime == null && typeDetail == null ){
            return;
        }
        Integer type = 1;
        if ("收入".equals(typeDetail)){
            type = 0;
        }else if (!"收入".equals(typeDetail) && !"支出".equals(typeDetail)){
            return;
        }
        Bill bill = new Bill();
        bill.setDetails(details);
        bill.setMoney(money);
        bill.setTagId(TagEnum.WEIXIN.id); //微信excel导入
        bill.setTagDetail(TagEnum.WEIXIN.name);
        bill.setType(type);
        bill.setRecordTime(recordTime);
        bill.setUserId(userId);
        //批量存储
        cachedDataList.add(bill);
        if (cachedDataList.size() >= BATCH_COUNT){
            billMapper.batchInsert(cachedDataList);
            BillServiceImpl.addExcelResList(cachedDataList);
            cachedDataList = new ArrayList<>();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        billMapper.batchInsert(cachedDataList);
        BillServiceImpl.addExcelResList(cachedDataList);
        System.out.println("导入完成" + cachedDataList.size());
    }
}
