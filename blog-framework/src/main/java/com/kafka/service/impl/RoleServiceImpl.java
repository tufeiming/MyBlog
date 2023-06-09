package com.kafka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kafka.domain.constant.SystemConstant;
import com.kafka.domain.dto.RoleDto;
import com.kafka.domain.dto.RoleListDto;
import com.kafka.domain.entity.Role;
import com.kafka.domain.entity.RoleMenu;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.PageVo;
import com.kafka.domain.vo.RoleVo;
import com.kafka.mapper.RoleMapper;
import com.kafka.service.RoleMenuService;
import com.kafka.service.RoleService;
import com.kafka.util.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Kafka
 * @since 2023-06-06 22:46:07
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeysByUserId(Long userId) {
        if (1L == userId) {
            ArrayList<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        return getBaseMapper().selectRoleKeysByUserId(userId);
    }

    @Override
    public ResponseResult<?> selectRolePage(RoleListDto roleListDto, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleListDto.getRoleName()), Role::getRoleName, roleListDto.getRoleName())
                .eq(StringUtils.hasText(roleListDto.getStatus()), Role::getStatus, roleListDto.getStatus())
                .orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Role> roles = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);
        PageVo<RoleVo> pageVo = new PageVo<>(roleVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> insertRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);
        if (roleDto.getMenuIds() != null && roleDto.getMenuIds().size() > 0) {
            insertRoleMenu(roleDto);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> getInfo(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> updateRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(roleDto);
        return ResponseResult.okResult();
    }

    @Override
    public List<Role> listAllRole() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemConstant.STATUS_NORMAL));
    }

    @Override
    public List<Long> selectRoleIdsByUserId(Long userId) {
        return getBaseMapper().selectRoleIdsByUserId(userId);
    }

    private void insertRoleMenu(RoleDto roleDto) {
        List<RoleMenu> roleMenus = roleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(roleDto.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
    }
}
