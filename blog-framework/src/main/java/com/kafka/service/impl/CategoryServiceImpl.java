package com.kafka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kafka.domain.constant.SystemConstant;
import com.kafka.domain.entity.Article;
import com.kafka.domain.entity.Category;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.CategoryVo;
import com.kafka.mapper.CategoryMapper;
import com.kafka.service.ArticleService;
import com.kafka.service.CategoryService;
import com.kafka.util.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author Kafka
 * @since 2023-05-25 13:13:39
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private ArticleService articleService;

    @Override
    public ResponseResult<?> getCategoryList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);

        Set<Long> categoryIdSet = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        List<Category> categoryList = listByIds(categoryIdSet);
        categoryList = categoryList.stream()
                .distinct()
                .filter(category -> SystemConstant.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
