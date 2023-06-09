package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.dto.TagListDto;
import com.kafka.domain.entity.Tag;
import com.kafka.domain.response.ResponseResult;


/**
 * 标签(Tag)表服务接口
 *
 * @author Kafka
 * @since 2023-06-05 17:46:20
 */
public interface TagService extends IService<Tag> {

    ResponseResult<?> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult<?> listAllTag();

    ResponseResult<?> getInfo(Long id);
}
