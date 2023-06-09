package com.kafka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kafka.domain.entity.User;
import com.kafka.domain.entity.UserRole;
import com.kafka.domain.response.AppHttpCode;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.PageVo;
import com.kafka.domain.vo.UserInfoVo;
import com.kafka.domain.vo.UserVo;
import com.kafka.exception.SystemException;
import com.kafka.mapper.UserMapper;
import com.kafka.service.UserRoleService;
import com.kafka.service.UserService;
import com.kafka.util.BeanCopyUtils;
import com.kafka.util.SecurityUtils;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author Kafka
 * @since 2023-05-28 14:58:21
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public ResponseResult<?> userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult<?> updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> register(User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCode.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCode.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCode.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickname())) {
            throw new SystemException(AppHttpCode.NICKNAME_NOT_NULL);
        }
        if (userNameExist(user.getUsername())) {
            throw new SystemException(AppHttpCode.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickname())) {
            throw new SystemException(AppHttpCode.NICKNAME_EXIST);
        }

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> selectUserPage(User user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(user.getUsername()), User::getUsername, user.getUsername())
                .eq(StringUtils.hasText(user.getStatus()), User::getStatus, user.getStatus())
                .eq(StringUtils.hasText(user.getPhoneNumber()), User::getPhoneNumber, user.getPhoneNumber());
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<User> users = page.getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);
        PageVo<UserVo> pageVo = new PageVo<>(userVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public boolean checkUserNameUnique(String username) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUsername, username)) == 0;
    }

    @Override
    public boolean checkPhoneUnique(String phoneNumber) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getPhoneNumber, phoneNumber)) == 0;
    }

    @Override
    public boolean checkEmailUnique(String email) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getEmail, email)) == 0;
    }

    @Override
    @Transactional
    public ResponseResult<?> addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        if (user.getRoleIds() != null && user.getRoleIds().size() > 0) {
            insertUserRole(user);
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, user.getId());
        userRoleService.remove(queryWrapper);
        insertUserRole(user);
        updateById(user);
    }

    private void insertUserRole(User user) {
        List<UserRole> userRoles = user.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
    }

    private boolean nickNameExist(String nickname) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickname, nickname);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return count(queryWrapper) > 0;
    }
}
