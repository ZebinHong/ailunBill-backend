package com.hunfeng.money.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunfeng.money.entity.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    List<Sum> getStatInHalfYear(@Param("userId") Long userId,
                                @Param("type") Integer type,
                                @Param("beginTime") String beginTime,
                                @Param("endTime") String endTime);
    List<Sum> getStatInMonth(@Param("userId") Long userId,
                             @Param("type") Integer type,
                             @Param("beginTime") String beginTime,
                             @Param("endTime") String endTime);

}
