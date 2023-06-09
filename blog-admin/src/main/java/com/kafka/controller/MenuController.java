package com.kafka.controller;

import com.kafka.domain.entity.Menu;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.MenuTreeVo;
import com.kafka.domain.vo.MenuVo;
import com.kafka.domain.vo.RoleMenuTreeSelectVo;
import com.kafka.service.MenuService;
import com.kafka.util.SystemConverter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult<?> list(Menu menu) {
        List<MenuVo> menuVos = menuService.selectMenuList(menu);
        return ResponseResult.okResult(menuVos);
    }

    @PostMapping
    public ResponseResult<?> add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getInfo(@PathVariable("id") Long id) {
        return menuService.getInfo(id);
    }

    @PutMapping
    public ResponseResult<?> edit(@RequestBody Menu menu) {
        return menuService.edit(menu);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable("id") Long id) {
        return menuService.deleteById(id);
    }

    @GetMapping("/treeSelect")
    public ResponseResult<?> treeSelect() {
        List<MenuVo> menuVos = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menuVos);
        return ResponseResult.okResult(menuTreeVos);
    }

    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult<?> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<MenuVo> menuVos = menuService.selectMenuList(new Menu());
        List<Long> menuIds = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menuVos);
        RoleMenuTreeSelectVo roleMenuTreeSelectVo = new RoleMenuTreeSelectVo(menuTreeVos, menuIds);
        return ResponseResult.okResult(roleMenuTreeSelectVo);
    }
}
