package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.dto.AddArticleDto;
import com.kafka.domain.dto.ArticleDto;
import com.kafka.domain.entity.Article;
import com.kafka.domain.response.ResponseResult;

public interface ArticleService extends IService<Article> {
    ResponseResult<?> hotArticleList();

    ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult<?> getArticleDetail(Long id);

    ResponseResult<?> updateViewCount(Long id);

    ResponseResult<?> add(AddArticleDto addArticleDto);

    ResponseResult<?> selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    ResponseResult<?> getInfo(Long id);

    ResponseResult<?> edit(ArticleDto articleDto);
}
