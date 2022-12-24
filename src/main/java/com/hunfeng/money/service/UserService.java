package com.hunfeng.money.service;

import com.hunfeng.money.entity.User;
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
public interface UserService extends IService<User> {
    User getUserByUsername(String username);

    User findUserByOpenId(String openID);
}
