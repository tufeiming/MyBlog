package com.kafka.service;

import com.kafka.domain.entity.User;
import com.kafka.domain.response.ResponseResult;

public interface BlogLoginService {

    ResponseResult<?> login(User user);
}
