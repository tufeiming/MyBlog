package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.entity.Article;
import com.kafka.domain.vo.HotArticleVo;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult<List<HotArticleVo>> hotArticleList();
}
