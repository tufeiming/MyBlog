package com.kafka.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kafka.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author Kafka
 * @since 2023-06-06 22:46:06
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeysByUserId(Long userId);

    List<Long> selectRoleIdsByUserId(Long userId);
}
