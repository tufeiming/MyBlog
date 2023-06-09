package com.kafka.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleMenuTreeSelectVo {
    // 菜单树
    private List<MenuTreeVo> menus;
    // 角色所关联的菜单权限id列表
    private List<Long> checkedKeys;
}