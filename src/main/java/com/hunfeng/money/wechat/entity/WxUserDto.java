package com.hunfeng.money.wechat.entity;

import com.hunfeng.money.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxUserDto implements Serializable {


    /**
     * 用户名
     */
    private String userName;

    /**
     * 传入参数：临时登录凭证
     */
    private String code;


    /**
     * 微信openId
     */
    private String openId;

    /**
     * 传入参数: 用户非敏感信息
     */
    private String rawData;

    /**
     * 传入参数: 签名
     */
    private String signature;

    /**
     * 传入参数: 用户敏感信息
     */
    private String encryptedData;

    /**
     * 传入参数: 解密算法的向量
     */
    private String iv;

    /**
     * 返回:服务器jwt token
     */
    private String token;

    /**
     * 返回：userName或openId对应的用户
     */
    private User user;
}
