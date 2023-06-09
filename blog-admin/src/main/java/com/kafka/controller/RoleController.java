package com.kafka.controller;

import com.kafka.domain.dto.ChangeRoleStatusDto;
import com.kafka.domain.dto.RoleDto;
import com.kafka.domain.dto.RoleListDto;
import com.kafka.domain.entity.Role;
import com.kafka.domain.response.ResponseResult;
import com.kafka.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult<?> list(RoleListDto roleListDto, Integer pageNum, Integer pageSize) {
        return roleService.selectRolePage(roleListDto, pageNum, pageSize);
    }

    @PutMapping("/changeStatus")
    public ResponseResult<?> changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        Role role = new Role();
        role.setId(changeRoleStatusDto.getRoleId());
        role.setStatus(changeRoleStatusDto.getStatus());
        roleService.updateById(role);
        return ResponseResult.okResult();
    }

    @PostMapping
    public ResponseResult<?> add(@RequestBody RoleDto roleDto) {
        return roleService.insertRole(roleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getInfo(@PathVariable("id") Long id) {
        return roleService.getInfo(id);
    }

    @PutMapping
    public ResponseResult<?> edit(@RequestBody RoleDto roleDto) {
        return roleService.updateRole(roleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> remove(@PathVariable(name = "id") Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }
    @GetMapping("/listAllRole")
    public ResponseResult<?> listAllRole() {
        List<Role> roles = roleService.listAllRole();
        return ResponseResult.okResult(roles);
    }
}
