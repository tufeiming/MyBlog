package com.kafka.service.impl;

import com.kafka.domain.entity.LoginUser;
import com.kafka.domain.entity.User;
import com.kafka.domain.response.ResponseResult;
import com.kafka.service.LoginService;
import com.kafka.util.JwtUtils;
import com.kafka.util.RedisCache;
import com.kafka.util.SecurityUtils;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminLoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult<?> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createJWT(userId);
        redisCache.setCacheObject("login:" + userId, loginUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult<?> logout() {
        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject("login:" + userId);
        return ResponseResult.okResult();
    }
}
