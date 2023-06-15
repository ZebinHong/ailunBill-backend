package com.hunfeng.money.service;

import com.hunfeng.money.entity.Bill;

import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    void addExcelResList(List<Bill> bills);
    List<Bill> batchImport(InputStream inputStream, Integer userId);
    List<Bill> alipayBatchImport(InputStream inputStream, Integer userId);
}
