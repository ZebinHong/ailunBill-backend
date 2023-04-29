package com.hunfeng.money.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunfeng.money.entity.Bill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunfeng.money.entity.BillDto;
import com.hunfeng.money.entity.Sum;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hunfeng
 * @since 2022-11-23
 */
public interface BillService extends IService<Bill> {
    Page<Bill> getBillsByUserId(Integer userId, Page<Bill> page, BillDto billDto) throws ParseException;
    Page<Bill> getDayBillsByUserId(Integer userId, Page<Bill> page, BillDto billDto);

    List<Bill> batchImport(InputStream inputStream, Integer userId);

    List<Sum> getStatInHalfYear(String monthYear,Long userId, Integer type);
    List<Sum> getStatInMonth(String monthYear, Long userId, Integer type);
    boolean removeBatch(String ids);

    List<Bill> alipayBatchImport(InputStream inputStream, Integer userId);
}
