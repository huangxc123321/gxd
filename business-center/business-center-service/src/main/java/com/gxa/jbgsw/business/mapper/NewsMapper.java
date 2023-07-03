package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.NewsRequest;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface NewsMapper extends BaseMapper<News> {

    List<News> pageQuery(NewsRequest request);
}
