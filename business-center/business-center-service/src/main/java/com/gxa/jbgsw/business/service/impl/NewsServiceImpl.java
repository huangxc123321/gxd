package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.News;
import com.gxa.jbgsw.business.mapper.NewsMapper;
import com.gxa.jbgsw.business.protocol.dto.NewsDTO;
import com.gxa.jbgsw.business.protocol.dto.NewsRequest;
import com.gxa.jbgsw.business.protocol.dto.SearchNewsRequest;
import com.gxa.jbgsw.business.protocol.dto.SearchNewsResponse;
import com.gxa.jbgsw.business.protocol.enums.IsFixedEnum;
import com.gxa.jbgsw.business.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Resource
    NewsMapper newsMapper;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        newsMapper.deleteBatchIds(list);
    }

    @Override
    public void add(NewsDTO newsDTO) {
        News news = mapperFacade.map(newsDTO, News.class);
        news.setCreateAt(new Date());

        // 是否定时发布:  0 不定时  1定时
        if(IsFixedEnum.SIGNED.equals(newsDTO.getIsFixed())){
            // 写定时任务
            String key = RedisKeys.NEWS_PUBLIS_TIME + news.getId();
            // 过期时间
            long timeout = DateUtil.between(new Date(), newsDTO.getPublishAt(), DateUnit.MINUTE);
            stringRedisTemplate.opsForValue().set(key, String.valueOf(news.getId()), timeout, TimeUnit.MINUTES);
        }

        newsMapper.insert(news);
    }

    @Override
    public void updateNews(NewsDTO newsDTO) {
        News news = newsMapper.selectById(newsDTO.getId());
        news.setUpdateAt(new Date());

        // newsDTO有null就不需要替换news的字段
        BeanUtils.copyProperties(newsDTO, news, CopyPropertionIngoreNull.getNullPropertyNames(news));
        newsMapper.updateById(news);
    }

    @Override
    public void updateStatus(String id, Integer status) {
        LambdaUpdateWrapper<News> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(News::getStatus, status)
                .eq(News::getId, id);
        newsMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public PageResult<News> pageQuery(NewsRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<News> responses = newsMapper.pageQuery(request);
        PageInfo<News> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<News>>() {
        }.build(), new TypeBuilder<PageResult<News>>() {}.build());
    }

    @Override
    public PageResult<SearchNewsResponse> queryNews(SearchNewsRequest searchNewsRequest) {
        PageHelper.startPage(searchNewsRequest.getPageNum(), searchNewsRequest.getPageSize());

        List<SearchNewsResponse> responses = newsMapper.queryNews(searchNewsRequest.getType());
        PageInfo<SearchNewsResponse> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<SearchNewsResponse>>() {
        }.build(), new TypeBuilder<PageResult<SearchNewsResponse>>() {}.build());
    }

}
