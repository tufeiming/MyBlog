package com.kafka.filter;

import com.alibaba.fastjson2.JSON;
import com.kafka.domain.entity.LoginUser;
import com.kafka.domain.response.AppHttpCode;
import com.kafka.domain.response.ResponseResult;
import com.kafka.util.JwtUtils;
import com.kafka.util.RedisCache;
import com.kafka.util.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 说明该接口不需要登录  直接放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析获取userid
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token超时  token非法
            // 响应告诉前端需要重新登录
            ResponseResult<?> result = ResponseResult.errorResult(AppHttpCode.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        // 从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if (loginUser == null) {
            // 说明登录过期，提示重新登录
            ResponseResult<?> result = ResponseResult.errorResult(AppHttpCode.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
