package com.hunfeng.money.controller;


import com.hunfeng.money.common.Result;
import com.hunfeng.money.entity.User;
import com.hunfeng.money.service.UserService;
import com.hunfeng.money.service.impl.UserServiceImpl;
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
@RequestMapping("/money/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("list")
    public List<User> getUserList(){
        return userService.list();
    }

    @GetMapping("get/{id}")
    public Result getUserById(@PathVariable("id")Long id){
        User userByID = userService.getById(id);
        if (userByID != null) {
            return Result.success("根据ID获得用户信息成功", userByID);
        } else {
            return Result.error("根据ID获得用户信息失败");
        }
    }
    @GetMapping("/get-by-username/{userName}")
    public Result getUserByUserName(@PathVariable("userName")String userName){
        User userByID = userService.getUserByUsername(userName);
        if (userByID != null) {
            return Result.success("根据ID获得用户信息成功", userByID);
        } else {
            return Result.error("根据ID获得用户信息失败");
        }
    }

    @PostMapping("add")
    public Result addUser(@RequestBody User user){
        boolean b = userService.save(user);
        if(b){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody User user){
        boolean b = userService.updateById(user);
        if(b){
            User res = userService.getById(user.getId());
            return Result.success("修改成功", res);
        }else{
            return Result.error("修改失败");
        }
    }
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable("id")Long id){
        boolean b = userService.removeById(id);
        if(b){
            return Result.success("修改成功");
        }else{
            return Result.error("修改失败");
        }
    }

}

