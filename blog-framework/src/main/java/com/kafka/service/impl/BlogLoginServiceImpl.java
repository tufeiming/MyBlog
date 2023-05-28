package com.kafka.service.impl;

import com.kafka.domain.entity.LoginUser;
import com.kafka.domain.entity.User;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.BlogUserLoginVo;
import com.kafka.domain.vo.UserInfoVo;
import com.kafka.service.BlogLoginService;
import com.kafka.util.BeanCopyUtils;
import com.kafka.util.JwtUtils;
import com.kafka.util.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult<?> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if (authentication == null) {
            throw new RuntimeException("用户名或密码错误！");
        }
        // 获取userId，生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtils.createJWT(userId.toString());
        // 把用户信息存入redis
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);
        // 把token和userInfo封装返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult<?> logout() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("bloglogin:" + userId);
        return ResponseResult.okResult();
    }
}
