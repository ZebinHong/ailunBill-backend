package com.hunfeng.money.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hunfeng.money.entity.User;
import com.hunfeng.money.mapper.UserMapper;
import com.hunfeng.money.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        User user = baseMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User findUserByOpenId(String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        User user = baseMapper.selectOne(queryWrapper);
        return user;
    }
}
