package com.kafka.domain.vo;

import com.kafka.domain.entity.Role;
import com.kafka.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRolesVo {
    private User user;
    private List<Role> roles;
    private List<Long> roleIds;
}
