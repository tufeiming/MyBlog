package com.kafka.controller;

import com.kafka.domain.entity.LoginUser;
import com.kafka.domain.entity.Menu;
import com.kafka.domain.entity.User;
import com.kafka.domain.response.AppHttpCode;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.AdminUserInfoVo;
import com.kafka.domain.vo.RoutersVo;
import com.kafka.domain.vo.UserInfoVo;
import com.kafka.exception.SystemException;
import com.kafka.service.LoginService;
import com.kafka.service.MenuService;
import com.kafka.service.RoleService;
import com.kafka.util.BeanCopyUtils;
import com.kafka.util.SecurityUtils;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Resource
    private LoginService loginService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult<?> login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCode.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @PostMapping("/user/logout")
    public ResponseResult<?> logout(){
        return loginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult<?> getInfo(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        List<String> perms = menuService.selectPermsByUserId(user.getId());
        List<String> roleKeys = roleService.selectRoleKeysByUserId(user.getId());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeys, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult<?> getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }
}
