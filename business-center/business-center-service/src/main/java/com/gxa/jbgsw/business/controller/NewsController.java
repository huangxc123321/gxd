package com.gxa.jbgsw.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxa.jbgsw.business.client.NewsApi;
import com.gxa.jbgsw.business.entity.News;
import com.gxa.jbgsw.business.feignapi.UserFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.NewsTypeEnum;
import com.gxa.jbgsw.business.service.NewsService;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "榜单管理")
public class NewsController implements NewsApi {
    @Resource
    NewsService newsService;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    MapperFacade mapperFacade;


    @Override
    public void add(NewsDTO newsDTO) {
        newsService.add(newsDTO);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        newsService.deleteBatchIds(ids);
    }

    @Override
    public PageResult<NewsResponse> pageQuery(NewsRequest request) {
        PageResult<NewsResponse> pages = new PageResult<>();

        PageResult<News> pageResult = newsService.pageQuery(request);
        List<News> list = pageResult.getList();
        if(CollectionUtils.isNotEmpty(list)){
            List<NewsResponse> responses = mapperFacade.mapAsList(list, NewsResponse.class);

            responses.forEach(s->{
                s.setTypeName(NewsTypeEnum.getNameByIndex(s.getType()));
            });
            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }
        
        return pages;
    }

    @Override
    public void update(NewsDTO newsDTO) {
        newsService.updateNews(newsDTO);
    }

    @Override
    public NewsDTO detail(Long id) {
        News news = newsService.getById(id);

        NewsDTO newsDTO = mapperFacade.map(news, NewsDTO.class);
        return newsDTO;
    }

    @Override
    public List<NewsResponse> getHotNews() {
        return newsService.getHotNews();
    }
}

