package com.kafka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kafka.domain.constant.SystemConstant;
import com.kafka.domain.entity.Comment;
import com.kafka.domain.response.AppHttpCode;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.CommentVo;
import com.kafka.domain.vo.PageVo;
import com.kafka.exception.SystemException;
import com.kafka.mapper.CommentMapper;
import com.kafka.service.CommentService;
import com.kafka.service.UserService;
import com.kafka.util.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author Kafka
 * @since 2023-05-28 13:58:00
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private UserService userService;

    @Override
    public ResponseResult<?> commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        var queryWrapper = new LambdaQueryWrapper<Comment>();
        queryWrapper.eq(SystemConstant.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId)
                .eq(Comment::getRootId, SystemConstant.COMMENT_NO_ROOT_COMMENT)
                .eq(Comment::getType, commentType)
                .orderByAsc(Comment::getCreateTime);
        var page = new Page<Comment>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(page.getRecords());
        // 查询所有根评论对应的子评论，并复制给对应的属性
        commentVos.forEach(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())));
        return ResponseResult.okResult(new PageVo<>(commentVos, page.getTotal()));
    }

    @Override
    public ResponseResult<?> addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCode.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> toCommentVoList(List<Comment> commentList) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);

        commentVos.forEach(commentVo -> {
            commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickname());
            Long toCommentUserId = commentVo.getToCommentUserId();
            if (toCommentUserId != -1) {
                commentVo.setToCommentUserName(userService.getById(toCommentUserId).getNickname());
            }
        });

        return commentVos;
    }

    private List<CommentVo> getChildren(Long id) {
        var queryWrapper = new LambdaQueryWrapper<Comment>();
        queryWrapper.eq(Comment::getRootId, id)
                .orderByDesc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        return toCommentVoList(comments);
    }
}
