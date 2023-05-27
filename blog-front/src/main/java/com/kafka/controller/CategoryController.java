package com.kafka.controller;

import com.kafka.domain.response.ResponseResult;
import com.kafka.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult<?> getCategoryList() {
        return categoryService.getCategoryList();
    }
}
