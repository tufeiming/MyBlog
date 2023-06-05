package com.kafka.controller;

import com.kafka.domain.response.ResponseResult;
import com.kafka.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Tag(name = "友链", description = "友链相关接口")
public class LinkController {
    @Resource
    private LinkService linkService;
    @GetMapping("/getAllLink")
    @Operation(summary = "友链评论列表", description = "获取一页友链评论")
    public ResponseResult<?> getAllLink() {
        return linkService.getAllLink();
    }
}
