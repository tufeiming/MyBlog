package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.entity.Category;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.PageVo;


/**
 * 分类表(Category)表服务接口
 *
 * @author Kafka
 * @since 2023-05-25 13:13:39
 */
public interface CategoryService extends IService<Category> {
    ResponseResult<?> getCategoryList();

    ResponseResult<?> listAllCategory();

    PageVo<Category> selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}
