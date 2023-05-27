package com.kafka.handler.security;

import com.alibaba.fastjson2.JSON;
import com.kafka.domain.response.AppHttpCode;
import com.kafka.domain.response.ResponseResult;
import com.kafka.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        authException.printStackTrace();
        ResponseResult<?> result;
        if (authException instanceof BadCredentialsException) {
            result = ResponseResult.errorResult(AppHttpCode.LOGIN_ERROR.getCode(), authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult(AppHttpCode.NEED_LOGIN);
        } else {
            result = ResponseResult.errorResult(AppHttpCode.SYSTEM_ERROR.getCode(), "认证或授权失败");
        }

        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
