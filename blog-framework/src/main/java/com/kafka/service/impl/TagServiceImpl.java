package com.kafka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kafka.domain.entity.Tag;
import com.kafka.mapper.TagMapper;
import com.kafka.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author Kafka
 * @since 2023-06-05 17:46:20
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
