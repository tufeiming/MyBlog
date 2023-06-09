package com.kafka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kafka.domain.entity.Link;
import com.kafka.domain.response.ResponseResult;
import com.kafka.domain.vo.LinkVo;
import com.kafka.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author Kafka
 * @since 2023-05-25 21:04:26
 */
public interface LinkService extends IService<Link> {

    ResponseResult<?> getAllLink();

    PageVo<LinkVo> selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}
