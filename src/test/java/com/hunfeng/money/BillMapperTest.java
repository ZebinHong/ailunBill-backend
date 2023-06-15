package com.hunfeng.money;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.entity.BillDto;
import com.hunfeng.money.entity.BillRespDto;
import com.hunfeng.money.mapper.BillMapper;
import com.hunfeng.money.service.BillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BillMapperTest {
    @Autowired
    private BillService billService;
    @Test
    public void testGetDayBills(){
//        long begin = System.currentTimeMillis();
//        BillDto billDto = new BillDto(null, "2022-11-15");
//        Page<Bill> billPage = billService.getDayBillsByUserId(17, new Page<>(1, 10),billDto);
//        long end = System.currentTimeMillis();
//        System.out.println("getDayBills:" + (end - begin));
//        System.out.println(billPage.getSize());
//        for (BillRespDto b : billPage.getRecords()){
//            System.out.println(b.toString());
//        }
    }
    @Test
    public void testGetDayBills2(){
//        long begin = System.currentTimeMillis();
//        BillDto billDto = new BillDto(null, "2022-11-15");
//        Page<BillRespDto> billPage = billService.getDayBillsByUserId(17, new Page<>(1, 10),billDto);
//        long end = System.currentTimeMillis();
//        System.out.println("getDayBills:" + (end - begin));
    }
}
