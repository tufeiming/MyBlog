package com.kafka.controller;

import com.kafka.domain.constant.SystemConstant;
import com.kafka.domain.dto.AddCommentDto;
import com.kafka.domain.response.ResponseResult;
import com.kafka.service.CommentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Tag(name = "评论", description = "评论相关接口")
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult<?> commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstant.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @GetMapping("/linkCommentList")
    @Parameters({
            @Parameter(name = "pageNum", description = "页号"),
            @Parameter(name = "pageSize", description = "每页大小")
    })
    public ResponseResult<?> linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstant.LINK_COMMENT, null, pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult<?> addComment(@RequestBody AddCommentDto addCommentDto) {
        return commentService.addComment(addCommentDto);
    }
}
