package com.kafka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kafka.domain.constant.SystemConstant;
import com.kafka.domain.entity.Article;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.HotArticleVo;
import com.kafka.mapper.ArticleMapper;
import com.kafka.service.ArticleService;
import com.kafka.util.BeanCopyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        // 查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是已发布文章，按照浏览量进行排序，限制10条
        queryWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1, SystemConstant.HOT_ARTICLE_SIZE);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        List<HotArticleVo> articleVos = BeanCopyUtil.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }
}
