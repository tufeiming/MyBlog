package com.kafka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kafka.domain.entity.Role;
import com.kafka.mapper.RoleMapper;
import com.kafka.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Kafka
 * @since 2023-06-06 22:46:07
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeysByUserId(Long userId) {
        if (1L == userId) {
            ArrayList<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        return getBaseMapper().selectRoleKeysByUserId(userId);
    }
}
