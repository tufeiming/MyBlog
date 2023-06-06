package com.kafka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kafka.domain.constant.SystemConstant;
import com.kafka.domain.entity.Menu;
import com.kafka.mapper.MenuMapper;
import com.kafka.service.MenuService;
import com.kafka.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author Kafka
 * @since 2023-06-06 22:38:52
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        // 如果是管理员，返回所有的权限
        if (1L == userId) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstant.MENU_TYPE_MENU, SystemConstant.MENU_TYPE_BUTTON)
                    .eq(Menu::getStatus, SystemConstant.MENU_STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            return menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        // 否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(userId);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus;
        if (SecurityUtils.isAdmin()) {
            menus = menuMapper.selectAllRouterMenu();
        } else {
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        return builderMenuTree(menus);
    }

    private List<Menu> builderMenuTree(List<Menu> menus) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(SystemConstant.MENU_NO_PARENT))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }

    private List<Menu> getChildren(Menu parentMenu, List<Menu> menus) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentMenu.getId()))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }
}
