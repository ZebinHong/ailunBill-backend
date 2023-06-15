package com.hunfeng.money.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunfeng.money.entity.Bill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunfeng.money.entity.BillDto;
import com.hunfeng.money.entity.BillRespDto;
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
    Page<BillRespDto> getBillsPage(Integer userId, Page<Bill> page, BillDto billDto) throws ParseException;
    Page<BillRespDto> getDayBillsPage(Integer userId, Page<Bill> page, BillDto billDto);
    List<Sum> getHalfYearStat(String monthYear,Long userId, Integer type);
    List<Sum> getDayOfMonthStat(String monthYear, Long userId, Integer type);
    boolean removeBatch(String ids);
}
