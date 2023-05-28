package com.kafka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kafka.domain.entity.LoginUser;
import com.kafka.domain.entity.User;
import com.kafka.domain.response.AppHttpCode;
import com.kafka.exception.SystemException;
import com.kafka.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        var queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        // 判断是否查询到用户，如果没查到抛出异常
        if (user == null) {
            throw new SystemException(AppHttpCode.REQUIRE_USERNAME);
        }
        // 返回用户信息
        return new LoginUser(user);
    }
}
