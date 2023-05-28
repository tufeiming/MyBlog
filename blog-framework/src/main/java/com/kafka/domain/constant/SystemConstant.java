package com.kafka.domain.constant;

public class SystemConstant {
    /**
     * 文章是草稿
     */
    public static final String ARTICLE_STATUS_DRAFT = "1";
    /**
     * 文章正常发布状态
     */
    public static final String ARTICLE_STATUS_NORMAL = "0";
    /**
     * 热门文章数量
     */
    public static final int HOT_ARTICLE_SIZE = 10;
    /**
     * 分类正常状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /**
     * 友链审核通过状态
     */
    public static final String LINK_STATUS_NORMAL = "0";
    /**
     * 评论无根评论
     */
    public static final Long COMMENT_NO_ROOT_COMMENT = -1L;
    /**
     * 文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 友链评论
     */
    public static final String LINK_COMMENT = "1";
}
