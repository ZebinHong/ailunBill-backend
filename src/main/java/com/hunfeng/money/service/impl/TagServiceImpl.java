package com.hunfeng.money.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hunfeng.money.entity.Tag;
import com.hunfeng.money.mapper.TagMapper;
import com.hunfeng.money.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hunfeng
 * @since 2022-11-23
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Override
    public List<Tag> getTaglist(Integer userId) {
        //先获取公共标签
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", 1);
        queryWrapper.or().eq(userId != null && userId != 1, "user_id", userId);
        return baseMapper.selectList(queryWrapper);
    }
}
