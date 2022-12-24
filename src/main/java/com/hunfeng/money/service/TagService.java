package com.hunfeng.money.service;

import com.hunfeng.money.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hunfeng
 * @since 2022-11-23
 */
public interface TagService extends IService<Tag> {
    String getNameByTagId(Integer tagId);

    List<Tag> getTaglist(Integer userId);
}
