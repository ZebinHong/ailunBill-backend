package com.hunfeng.money.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunfeng.money.common.Result;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.entity.BillDto;
import com.hunfeng.money.entity.Sum;
import com.hunfeng.money.service.BillService;
import com.hunfeng.money.service.impl.BillServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hunfeng
 * @since 2022-11-23
 */
@RestController
//@CrossOrigin
@RequestMapping("/money/bill")
public class BillController {
    @Autowired
    BillService billService;

    @ApiOperation("根据用户id获取账单")
    @PostMapping("list/{userId}/{pageNum}/{pageSize}")
    public Result getBillsByUserId(@PathVariable("userId")Integer userId,
                                   @PathVariable("pageNum")Integer pageNum,
                                   @PathVariable("pageSize")Integer pageSize,
                                   @RequestBody BillDto billDto) throws ParseException {
        Page<Bill> page = new Page(pageNum, pageSize);
        Page<Bill> billPage = billService.getBillsByUserId(userId,page,billDto);
        if(billPage == null || billPage.getRecords() == null){
            return Result.error("获取失败");
        }else{
            return Result.success("获取成功",billPage);
        }
    }
    @ApiOperation("根据用户id获取某天账单")
    @PostMapping("list-day/{userId}/{pageNum}/{pageSize}")
    public Result getDayBillsByUserId(@PathVariable("userId")Integer userId,
                                   @PathVariable("pageNum")Integer pageNum,
                                   @PathVariable("pageSize")Integer pageSize,
                                   @RequestBody BillDto billDto){
        Page<Bill> page = new Page(pageNum, pageSize);
        Page<Bill> billPage = billService.getDayBillsByUserId(userId,page,billDto);
        if(billPage == null || billPage.getRecords() == null){
            return Result.error("获取失败");
        }else{
            return Result.success("获取成功",billPage);
        }
    }

    @ApiOperation("根据id获取账单")
    @GetMapping("get/{id}")
    public Result getBill(@PathVariable("id")Long id){
        Bill bill = billService.getById(id);
        if(bill == null){
            return Result.error("获取失败");
        }else{
            return Result.success("获取成功",bill);
        }
    }

    @ApiOperation("根据id删除账单")
    @DeleteMapping("delete/{id}")
    public Result deleteBill(@PathVariable("id")Long id){
        boolean b = billService.removeById(id);
        if(!b){
            return Result.error("删除失败");
        }else{
            return Result.success("删除成功");
        }
    }

    @ApiOperation("添加账单")
    @PostMapping("add")
    public Result addBill(@RequestBody Bill bill){
        boolean b = billService.save(bill);
        if(!b){
            return Result.error("添加失败");
        }else{
            return Result.success("添加成功");
        }
    }

    @ApiOperation("更新账单")
    @PutMapping("update")
    public Result updateBill(@RequestBody Bill bill){
        boolean b = billService.updateById(bill);
        if(!b){
            return Result.error("更新失败");
        }else{
            return Result.success("更新成功");
        }
    }

    @ApiOperation("获取近半年的统计")
    @GetMapping("/getStatInHalfYear/{monthYear}/{userId}/{type}")
    public Result getStatInHalfYear(@PathVariable("monthYear")String monthYear,
                                    @PathVariable("userId")Long userId,
                                    @PathVariable("type")Integer type){
        List<Sum> sumList = null;
        //对type=2做特殊处理
        if (type.intValue() == 2){
            List<Sum> t1 = billService.getStatInHalfYear(monthYear, userId, 0);
            List<Sum> t2 = billService.getStatInHalfYear(monthYear, userId, 1);
            for (int i = 0; i < t1.size(); i ++ ){
                double d = t1.get(i).getTotal() - t2.get(i).getTotal();
                //保留小数点后两位
                BigDecimal bg = new BigDecimal(d);
                d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                t1.get(i).setTotal(d);
            }
            sumList = t1;
        }else {
            sumList = billService.getStatInHalfYear(monthYear, userId, type);
        }
        if(sumList == null){
            return Result.error("获取年份收入账单列表失败");
        }else{
            return Result.success("获取年份收入账单列表成功",sumList);
        }
    }

    @ApiOperation("获取某个月的统计")
    @GetMapping("/getStatInMonth/{monthYear}/{userId}/{type}")
    public Result getStatInMonth(@PathVariable("monthYear")String monthYear,
                                    @PathVariable("userId")Long userId,
                                    @PathVariable("type")Integer type){
        List<Sum> sumList = null;
        if (type.intValue() == 2){
            List<Sum> t1 = billService.getStatInMonth(monthYear, userId, 0);
            List<Sum> t2 = billService.getStatInMonth(monthYear, userId, 1);
            DecimalFormat df = new DecimalFormat("#.00");
            for (int i = 0; i < t1.size(); i ++ ){
                double d = t1.get(i).getTotal() - t2.get(i).getTotal();
                //保留小数点后两位
                BigDecimal bg = new BigDecimal(d);
                d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                t1.get(i).setTotal(d);
            }
            sumList = t1;
        }else {
            sumList = billService.getStatInMonth(monthYear, userId, type);
        }
        if(sumList == null){
            return Result.error("获取月统计失败");
        }else{
            return Result.success("获取月统计成功",sumList);
        }
    }

    @ApiOperation("批量删除账单")
    @DeleteMapping("remove-batch")
    public Result removeBillBatch(@RequestBody String ids){
        JSONObject jsonObject = JSON.parseObject(ids);
        ids = jsonObject.getString("ids");
        boolean b = billService.removeBatch(ids);
        if(!b){
            return Result.error("删除失败");
        }else{
            return Result.success("删除成功");
        }
    }


}

