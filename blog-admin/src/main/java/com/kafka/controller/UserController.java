package com.kafka.controller;

import com.kafka.domain.entity.Role;
import com.kafka.domain.entity.User;
import com.kafka.domain.response.AppHttpCode;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.UserInfoAndRolesVo;
import com.kafka.exception.SystemException;
import com.kafka.service.RoleService;
import com.kafka.service.UserService;
import com.kafka.util.SecurityUtils;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult<?> list(User user, Integer pageNum, Integer pageSize) {
        return userService.selectUserPage(user, pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult<?> add(@RequestBody User user) {
        if (!StringUtils.hasText(user.getNickname())) {
            throw new SystemException(AppHttpCode.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUsername())) {
            throw new SystemException(AppHttpCode.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user.getPhoneNumber())) {
            throw new SystemException(AppHttpCode.PHONE_NUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user.getEmail())) {
            throw new SystemException(AppHttpCode.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult<?> remove(@PathVariable("ids") List<Long> ids) {
        if (ids.contains(SecurityUtils.getUserId())) {
            return ResponseResult.errorResult(AppHttpCode.DELETE_CURRENT_USER);
        }
        userService.removeByIds(ids);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getUserInfoAndRoles(@PathVariable("id") Long id) {
        List<Role> roles = roleService.listAllRole();
        User user = userService.getById(id);
        List<Long> roleIds = roleService.selectRoleIdsByUserId(id);
        UserInfoAndRolesVo userInfoAndRolesVo = new UserInfoAndRolesVo(user, roles, roleIds);
        return ResponseResult.okResult(userInfoAndRolesVo);
    }

    @PutMapping
    public ResponseResult<?> edit(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseResult.okResult();
    }
}
