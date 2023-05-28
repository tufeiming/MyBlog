package com.kafka.controller;

import com.kafka.domain.entity.User;
import com.kafka.domain.response.AppHttpCode;
import com.kafka.domain.response.ResponseResult;
import com.kafka.exception.SystemException;
import com.kafka.service.BlogLoginService;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {
    @Resource
    private BlogLoginService blogLoginService;
    @PostMapping("/login")

    public ResponseResult<?> login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCode.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult<?> logout() {
        return blogLoginService.logout();
    }
}
