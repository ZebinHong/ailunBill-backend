package com.hunfeng.money.wechat.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hunfeng.money.entity.User;
import com.hunfeng.money.service.UserService;
import com.hunfeng.money.wechat.entity.WxUserDto;
import com.hunfeng.money.wechat.service.WxMiniApi;
import com.hunfeng.money.wechat.service.WxUserLoginService;
import com.hunfeng.money.wechat.utils.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@Component
public class WxUserLoginServiceImpl implements WxUserLoginService {



    public static String appid;


    public static String secret;

    @Value("${WeChat.appid}")
    public void setAppid(String appid) {
        WxUserLoginServiceImpl.appid = appid;
    }

    @Value("${WeChat.secret}")
    public void setSecret(String secret) {
        WxUserLoginServiceImpl.secret = secret;
    }

    @Autowired
    private WxMiniApi wxMiniApi;


    @Autowired
    private UserService userService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxUserDto UserLoginAndRegister(WxUserDto wxUserDto, HttpServletResponse response) {
        String jscode = wxUserDto.getCode();
        if(!StringUtils.isEmpty(jscode)){
            JSONObject jsonObject = wxMiniApi.authCode2Session(appid, secret, jscode);
            if(jsonObject==null){
                throw new RuntimeException("小程序用户授权失败");
            }
            String openID = jsonObject.getString("openid");
            String session_key = jsonObject.getString("session_key");
            if (StringUtils.isEmpty(openID)) {
                return null;
            }
            wxUserDto.setOpenId(openID);
            response.setHeader("Authorization",openID);
            //查询用户表中是否存在改用户 如果存在 则返回这个用户 信息 如果不存在 则进行新增用户并返回数据
            User userByOpenId = userService.findUserByOpenId(openID);
            //如果不存在 则新增用户
            if (userByOpenId==null) {
                String userInfo = WeChatUtil.decryptData(wxUserDto.getEncryptedData(), session_key, wxUserDto.getIv());
                User user = JSONObject.parseObject(userInfo, User.class);
                System.out.println(user);
                user.setName(user.getNickName());
                user.setOpenId(openID);
                Boolean insertUser = userService.save(user);
                //如果插入成功 则返回给前端
                if(insertUser){
                    response.setHeader("userId", String.valueOf(user.getId()));
                    wxUserDto.setUser(user);
                    return wxUserDto;
                }
                return null;
            }
            else{
                //如果存在 则将获取到的信息返回
                response.setHeader("userId", String.valueOf(userByOpenId.getId()));
                wxUserDto.setUser(userByOpenId);
            }
            return wxUserDto;
        }else{
            return null;
        }
    }
}

