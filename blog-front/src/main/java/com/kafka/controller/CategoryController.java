package com.kafka.controller;

import com.kafka.domain.entity.Category;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.CategoryVo;
import com.kafka.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult<List<CategoryVo>> getCategoryList() {
        return categoryService.getCategoryList();
    }
}
