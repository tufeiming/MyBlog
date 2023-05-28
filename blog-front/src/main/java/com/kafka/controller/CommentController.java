package com.kafka.controller;

import com.kafka.domain.constant.SystemConstant;
import com.kafka.domain.entity.Comment;
import com.kafka.domain.response.ResponseResult;
import com.kafka.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult<?> commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstant.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult<?> linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstant.LINK_COMMENT, null, pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult<?> addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }
}
