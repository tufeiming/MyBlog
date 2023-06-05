package com.kafka.runner;

import com.kafka.domain.entity.Article;
import com.kafka.mapper.ArticleMapper;
import com.kafka.util.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private RedisCache redisCache;

    @Override
    public void run(String... args) {
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> map = articles.stream().
                collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));
        redisCache.setCacheMap("article:viewCount", map);
    }
}
