package com.hunfeng.money.wechat.service;

import com.hunfeng.money.wechat.entity.WxUserDto;

import javax.servlet.http.HttpServletResponse;

public interface WxUserLoginService {
    WxUserDto UserLoginAndRegister(WxUserDto wxUserDto, HttpServletResponse response);
}
