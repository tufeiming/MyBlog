package com.kafka.controller;

import com.kafka.domain.dto.AddTagDto;
import com.kafka.domain.dto.EditTagDto;
import com.kafka.domain.dto.TagListDto;
import com.kafka.domain.entity.Tag;
import com.kafka.domain.response.ResponseResult;
import com.kafka.service.TagService;
import com.kafka.util.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<?> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult<?> listAllTag() {
        return tagService.listAllTag();
    }

    @PostMapping
    public ResponseResult<?> add(@RequestBody AddTagDto addTagDto) {
        Tag tag = BeanCopyUtils.copyBean(addTagDto, Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable("id") Long id) {
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getInfo(@PathVariable("id") Long id) {
        return tagService.getInfo(id);
    }

    @PutMapping
    public ResponseResult<?> edit(@RequestBody EditTagDto editTagDto) {
        Tag tag = BeanCopyUtils.copyBean(editTagDto, Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }
}
