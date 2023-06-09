package com.kafka.controller;

import com.kafka.domain.dto.AddArticleDto;
import com.kafka.domain.dto.ArticleDto;
import com.kafka.domain.entity.Article;
import com.kafka.domain.response.ResponseResult;
import com.kafka.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping
    public ResponseResult<?> add(@RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult<?> list(Article article, Integer pageNum, Integer pageSize) {
        return articleService.selectArticlePage(article, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getInfo(@PathVariable("id") Long id) {
        return articleService.getInfo(id);
    }

    @PutMapping
    public ResponseResult<?> edit(@RequestBody ArticleDto articleDto) {
       return articleService.edit(articleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable("id") Long id) {
        articleService.removeById(id);
        return ResponseResult.okResult();
    }
}
