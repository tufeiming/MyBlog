package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author Kafka
 * @since 2023-06-06 22:38:52
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
