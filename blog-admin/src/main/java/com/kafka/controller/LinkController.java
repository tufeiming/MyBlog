package com.kafka.controller;

import com.kafka.domain.entity.Link;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.LinkVo;
import com.kafka.domain.vo.PageVo;
import com.kafka.service.LinkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("content/link")
public class LinkController {
    @Resource
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult<?> list(Link link, Integer pageNum, Integer pageSize) {
        PageVo<LinkVo> pageVo = linkService.selectLinkPage(link, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @PostMapping
    public ResponseResult<?> add(@RequestBody Link link) {
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getInfo(@PathVariable("id") Long id) {
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

    @PutMapping
    public ResponseResult<?> edit(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> remove(@PathVariable("id") Long id) {
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
