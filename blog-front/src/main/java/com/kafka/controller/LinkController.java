package com.kafka.controller;

import com.kafka.domain.response.ResponseResult;
import com.kafka.service.LinkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {
    @Resource
    private LinkService linkService;
    @GetMapping("/getAllLink")
    public ResponseResult<?> getAllLink() {
        return linkService.getAllLink();
    }
}
