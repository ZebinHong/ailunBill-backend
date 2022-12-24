package com.hunfeng.money.wechat.controller;

import com.hunfeng.money.common.Result;
import com.hunfeng.money.wechat.entity.WxUserDto;
import com.hunfeng.money.wechat.service.WxUserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/money/wechat/user")
public class UserLoginController {


    @Autowired
    private WxUserLoginService wxUserLoginService;

    @PostMapping("/login")
    public Result login(@RequestBody WxUserDto wxUserDto, HttpServletResponse response){
        WxUserDto wxUserDto1= wxUserLoginService.UserLoginAndRegister(wxUserDto, response);
        return Result.success("登录成功",wxUserDto1);

    }



}