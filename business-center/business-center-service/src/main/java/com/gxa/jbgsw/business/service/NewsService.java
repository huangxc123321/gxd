package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.DetailInfoDTO;
import com.gxa.jbgsw.business.protocol.dto.NewsDTO;
import com.gxa.jbgsw.business.protocol.dto.NewsRequest;
import com.gxa.jbgsw.common.utils.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface NewsService extends IService<News> {

    void deleteBatchIds(Long[] ids);

    void add(NewsDTO newsDTO);

    void updateNews(NewsDTO newsDTO);

    void updateStatus(String id, Integer code);

    PageResult<News> pageQuery(NewsRequest request);
}
