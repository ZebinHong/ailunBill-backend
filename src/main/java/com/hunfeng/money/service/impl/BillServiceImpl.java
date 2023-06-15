package com.hunfeng.money.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunfeng.money.config.DateTimeZoneStringConverter;
import com.hunfeng.money.entity.*;
import com.hunfeng.money.listener.AlipayExcelBillDataListener;
import com.hunfeng.money.listener.ExcelBillDataListener;
import com.hunfeng.money.mapper.BillMapper;
import com.hunfeng.money.myenum.TagEnum;
import com.hunfeng.money.service.BillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunfeng.money.service.TagService;
import io.swagger.annotations.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hunfeng
 * @since 2022-11-23
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {
    @Autowired
    TagService tagService;

    @Autowired
    private BillMapper billMapper;

    @Override
    public Page<BillRespDto> getBillsPage(Integer userId, Page<Bill> page, BillDto billDto) throws ParseException {
        //对日期进行处理，输入的日期格式为yyyy-MM
        String date = billDto.getDate();
        date += "-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        String startDate = "";
        String endDate = "";
        // 设置cal为当月最小日期
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        //获取当月最小日期
        startDate = sdf.format(cal.getTime());
        // 设置cal为当月最大日期
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        //获取当月最小日期
        endDate = sdf.format(cal.getTime());
        Page<BillRespDto> billsPage = billMapper.getBillsPage(page,userId,startDate,endDate,billDto.getTagId());
        return billsPage;
    }

    @Override
    public Page<BillRespDto> getDayBillsPage(Integer userId, Page<Bill> page, BillDto billDto) {
        String date = billDto.getDate();
        Integer tagId = billDto.getTagId();
        String startDate = date + " 00:00:00", endDate = date + " 23:59:59";
        Page<BillRespDto> bills = billMapper.getBillsPage(page,userId, startDate, endDate, tagId);
        return bills;
    }

    @Override
    public List<Sum> getDayOfMonthStat(String monthYear, Long userId, Integer type) {
        String beginTime = monthYear + "-01 00:00:00";
        String[] split = monthYear.split("-");
        Calendar c = Calendar.getInstance();
        int day = 0;    //该月的天数
        int y = Integer.parseInt(split[0]), m = Integer.parseInt(split[1]);
        if (c.get(Calendar.YEAR) == y && (c.get(Calendar.MONTH) + 1) == m){ //若是本月，则统计到当前的天数即可
            day = c.get(Calendar.DATE);
        }else {
            c.set(Calendar.YEAR, y);
            c.set(Calendar.MONTH, m - 1);
            day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        String endTime = monthYear + "-" + day + " 23:59:59";
        List<Sum> sums = billMapper.getDayOfMonthStat(userId, type, beginTime, endTime);
        if (sums.size() == day) return sums;
        //补充没有数据的天数
        List<Sum> targetList = new ArrayList<>();
        for (int i = 0; i < day; i ++ ){
            targetList.add(new Sum((i + 1), m, y, 0));
        }
        for (int i = 0; i < targetList.size(); i ++ ){
            if (i >= sums.size()) sums.add(targetList.get(i));
            else if (!(targetList.get(i).getYear() == sums.get(i).getYear()
                    && targetList.get(i).getMonth() == sums.get(i).getMonth()
                    && targetList.get(i).getDay() == sums.get(i).getDay())){
                sums.add(i, targetList.get(i));
            }
        }
        return sums;
    }

    @Override
    public List<Sum> getHalfYearStat(String monthYear,Long userId, Integer type) {
        Calendar c = Calendar.getInstance();
        String[] split = monthYear.split("-");
        c.set(Calendar.YEAR, Integer.parseInt(split[0]));
        c.set(Calendar.MONTH, Integer.parseInt(split[1]));
        c.add(Calendar.MONTH, -5);
        int curM = c.get(Calendar.MONTH), curY = c.get(Calendar.YEAR);
        if (c.get(Calendar.MONTH) == 0){
            //月份为0表明是12月
            curM = 12;
            curY -- ;
        }
        String startTime = curY + "-" + curM +"-01 00:00:00"; //六个月前
        c.add(Calendar.MONTH, 6);
        String endTime = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH)+"-01 00:00:00"; //六个月后
        List<Sum> sums = billMapper.getHalfYearStat(userId, type, startTime, endTime);
        //补充没有数据的月份，此处用的算法是准备一个理想数组，与之进行比较，若该月份没有则往数组里添加月份。
        List<Sum> targetList = new ArrayList<>();
        while (targetList.size() < 6){
            targetList.add(new Sum(0, curM, curY, 0));
            curM ++ ;
            if (curM > 12){
                curY ++ ;
                curM = 1;
            }
        }
        if (sums == null) return targetList;
        else if (sums.size() == 6) return sums;
        for (int i = 0; i < targetList.size(); i ++ ){
            if (i >= sums.size()){
                sums.add(targetList.get(i));
            }else {
                int year = targetList.get(i).getYear();
                int month = targetList.get(i).getMonth();
                if (sums.get(i).getYear() != year || sums.get(i).getMonth() != month){
                    sums.add(i, targetList.get(i));
                }
            }
        }
        return sums;
    }

    @Override
    public boolean removeBatch(String ids) {
        List<Long> idList = new ArrayList<>();
        String[] ss = ids.split(" ");
        //去重
        HashSet<Long> set = new HashSet<>();
        for (int i = 0; i < ss.length; i ++ ){
            if (StringUtils.isEmpty(ss[i])) continue;
            long num = Long.parseLong(ss[i]);
            if (!set.contains(num)){
                idList.add(num);
            }
            set.add(num);
        }
        int i = billMapper.deleteBatchIds(idList);
        return i == idList.size();
    }


}
