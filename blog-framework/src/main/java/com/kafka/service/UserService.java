package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.entity.User;
import com.kafka.domain.response.ResponseResult;


/**
 * 用户表(User)表服务接口
 *
 * @author Kafka
 * @since 2023-05-28 14:58:21
 */
public interface UserService extends IService<User> {

    ResponseResult<?> userInfo();

    ResponseResult<?> updateUserInfo(User user);

    ResponseResult<?> register(User user);
}
