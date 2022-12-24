package com.hunfeng.money.controller;


import com.hunfeng.money.common.Result;
import com.hunfeng.money.entity.Bill;
import com.hunfeng.money.entity.Tag;
import com.hunfeng.money.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/money/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation("添加标签")
    @PostMapping("add")
    public Result addBill(@RequestBody Tag tag){
        boolean b = tagService.save(tag);
        if(!b){
            return Result.error("添加失败");
        }else{
            return Result.success("添加成功");
        }
    }

    @ApiOperation("通过标签id获取标签")
    @GetMapping("get/{id}")
    public Result getTagById(@PathVariable("id") Integer id){
        Tag tag = tagService.getById(id);
        if(tag == null){
            return Result.error("获取失败");
        }else{
            return Result.success("获取成功", tag);
        }
    }

    @ApiOperation("通过用户id获取标签列表")
    @GetMapping("list/{userId}")
    public Result getTaglist(@PathVariable("userId") Integer userId){
        List<Tag> tags = tagService.getTaglist(userId);
        if(tags == null || tags.size() <= 0){
            return Result.error("获取失败");
        }else{
            return Result.success("获取成功", tags);
        }
    }
}

