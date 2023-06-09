package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.dto.RoleDto;
import com.kafka.domain.dto.RoleListDto;
import com.kafka.domain.entity.Role;
import com.kafka.domain.response.ResponseResult;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author Kafka
 * @since 2023-06-06 22:46:07
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeysByUserId(Long userId);

    ResponseResult<?> selectRolePage(RoleListDto roleListDto, Integer pageNum, Integer pageSize);

    ResponseResult<?> insertRole(RoleDto roleDto);

    ResponseResult<?> getInfo(Long id);

    ResponseResult<?> updateRole(RoleDto roleDto);

    List<Role> listAllRole();

    List<Long> selectRoleIdsByUserId(Long userId);
}
