package com.hunfeng.money.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.hunfeng.money.entity.AlipayExcelBillData;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.mapper.BillMapper;
import com.hunfeng.money.myenum.TagEnum;
import com.hunfeng.money.service.impl.BillServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class AlipayExcelBillDataListener extends AnalysisEventListener<AlipayExcelBillData> {
    private BillMapper billMapper;
    private Integer userId;
    private static final int BATCH_COUNT = 50;
    private List<Bill> cachedDataList = new ArrayList<>();
    public AlipayExcelBillDataListener(BillMapper billMapper, Integer userId){
        this.billMapper = billMapper;
        this.userId = userId;
    }
    @Override
    public void invoke(AlipayExcelBillData data, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据:"+ data);
        String money = data.getMoney();
        String details = data.getDetails();
        Date recordTime = data.getRecordTime();
        String typeDetail = data.getTypeDetail();
        String classify = data.getClassify();
        if (money == null && details == null && recordTime == null && typeDetail == null ){
            return;
        }else if (money != null && Double.compare(Double.parseDouble(money), 0.0) == 0) {
            return;
        }
        Integer type = 1;
        if ("收入".equals(typeDetail) || "退款".equals(classify)){
            type = 0;
        }
        Bill bill = new Bill();
        bill.setMoney(Double.parseDouble(money));
        bill.setDetails(details);
        bill.setTagId(TagEnum.ALIPAY.id); //微信excel导入
        bill.setTagDetail(TagEnum.ALIPAY.name);
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
