package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author Kafka
 * @since 2023-06-06 22:46:07
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeysByUserId(Long userId);
}
