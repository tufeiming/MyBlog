package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.dto.AddCommentDto;
import com.kafka.domain.entity.Comment;
import com.kafka.domain.response.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author Kafka
 * @since 2023-05-28 13:58:00
 */
public interface CommentService extends IService<Comment> {

    ResponseResult<?> commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult<?> addComment(AddCommentDto addCommentDto);
}
