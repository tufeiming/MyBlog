package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.entity.Article;
import com.kafka.domain.response.ResponseResult;

public interface ArticleService extends IService<Article> {
    ResponseResult<?> hotArticleList();

    ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult<?> getArticleDetail(Long id);
}
