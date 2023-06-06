package com.kafka.controller;

import com.kafka.domain.response.ResponseResult;
import com.kafka.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Resource
    private TagService tagService;
    @GetMapping("/list")
    public ResponseResult<?> list() {
        return ResponseResult.okResult(tagService.list());
    }
}
