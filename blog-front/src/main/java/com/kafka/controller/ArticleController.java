package com.kafka.controller;

import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.HotArticleVo;
import com.kafka.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        return articleService.hotArticleList();
    }
}
