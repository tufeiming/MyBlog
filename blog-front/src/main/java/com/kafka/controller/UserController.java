package com.kafka.controller;

import com.kafka.annotation.SystemLog;
import com.kafka.domain.entity.User;
import com.kafka.domain.response.ResponseResult;
import com.kafka.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "用户", description = "用户相关接口")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/userInfo")
    public ResponseResult<?> userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult<?> updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody User user) {
        return userService.register(user);
    }
}
