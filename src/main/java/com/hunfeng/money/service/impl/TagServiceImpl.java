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
    public String getNameByTagId(Integer tagId) {
        Tag tag = baseMapper.selectById(tagId);
        return tag.getName();
    }

    @Override
    public List<Tag> getTaglist(Integer userId) {
        //先获取公共标签
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", 1);
        List<Tag> tags1 = baseMapper.selectList(queryWrapper);
        //再获取本人添加的标签
        QueryWrapper<Tag> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", userId);
        List<Tag> tags2 = baseMapper.selectList(queryWrapper2);
        if (userId.intValue() != 1 && tags2 != null && tags2.size() > 0){
            tags1.addAll(tags2);
        }
        return tags1;
    }
}
