package com.hunfeng.money.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunfeng.money.entity.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hunfeng.money.entity.BillRespDto;
import com.hunfeng.money.entity.Sum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hunfeng
 * @since 2022-11-23
 */
public interface BillMapper extends BaseMapper<Bill> {
    List<Sum> getHalfYearStat(@Param("userId") Long userId,
                                @Param("type") Integer type,
                                @Param("beginTime") String beginTime,
                                @Param("endTime") String endTime);
    List<Sum> getDayOfMonthStat(@Param("userId") Long userId,
                             @Param("type") Integer type,
                             @Param("beginTime") String beginTime,
                             @Param("endTime") String endTime);

    int batchInsert(@Param("list")List<Bill> cachedDataList);


    Page<BillRespDto> getBillsPage(Page<Bill> page,
                                   @Param("userId") Integer userId,
                                   @Param("startDate") String startDate,
                                   @Param("endDate")String endDate,
                                   @Param("tagId")Integer tagId);
}
