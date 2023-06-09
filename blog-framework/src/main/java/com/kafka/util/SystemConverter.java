package com.kafka.util;

import com.kafka.domain.vo.MenuTreeVo;
import com.kafka.domain.vo.MenuVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SystemConverter {
    private SystemConverter() {

    }

    public static List<MenuTreeVo> buildMenuSelectTree(List<MenuVo> menuVos) {
        List<MenuTreeVo> menuTreeVos = menuVos.stream()
                .map(menuVo -> new MenuTreeVo(menuVo.getId(), menuVo.getMenuName(), menuVo.getParentId(), null))
                .collect(Collectors.toList());
        return menuTreeVos.stream()
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(0L))
                .map(menuTreeVo -> menuTreeVo.setChildren(getChildList(menuTreeVos, menuTreeVo)))
                .collect(Collectors.toList());
    }

    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo menuTreeVo) {
        return list.stream()
                .filter(elem -> Objects.equals(elem.getParentId(), menuTreeVo.getId()))
                .map(elem -> elem.setChildren(getChildList(list, elem)))
                .collect(Collectors.toList());
    }
}
