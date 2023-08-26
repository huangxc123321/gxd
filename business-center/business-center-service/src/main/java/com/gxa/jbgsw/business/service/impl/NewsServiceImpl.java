package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.News;
import com.gxa.jbgsw.business.mapper.NewsMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.IsFixedEnum;
import com.gxa.jbgsw.business.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
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
        String imageUrl = getUrl(newsDTO.getContent());
        if(StrUtil.isNotBlank(imageUrl)){
            news.setPicture(imageUrl);
        }

        // 是否定时发布:  0 不定时  1定时
        if(IsFixedEnum.SIGNED.getCode().equals(newsDTO.getIsFixed())){
            news.setFixedAt(newsDTO.getFixedAt());
        }else{
            news.setFixedAt(null);
            // 立即发布
            news.setPublishAt(new Date());
        }
        newsMapper.insert(news);

        // 是否定时发布:  0 不定时  1定时
        if(IsFixedEnum.SIGNED.getCode().equals(newsDTO.getIsFixed())){
            // 写定时任务
            String key = RedisKeys.NEWS_PUBLIS_TIME + news.getId();
            // 过期时间
            long timeout = DateUtil.between(new Date(), newsDTO.getFixedAt(), DateUnit.MINUTE);
            stringRedisTemplate.opsForValue().set(key, String.valueOf(news.getId()), timeout, TimeUnit.MINUTES);
        }
    }

    private String getUrl(String content){
        int index = content.indexOf("<img src=");
        if( index != -1){
            // 剩下的内容
            String subContent = content.substring(index);
            // 第一张图片的结束地址
            int oneEnd = subContent.indexOf("/>");
            String imageUrl = subContent.substring(10, oneEnd-1);
            // 判断是否有引号
            imageUrl = imageUrl.replace("\"", "");
            return imageUrl;
        }
        return null;
    }


    @Override
    public void updateNews(NewsDTO newsDTO) {
        News news = newsMapper.selectById(newsDTO.getId());
        news.setUpdateAt(new Date());

        // newsDTO有null就不需要替换news的字段
        BeanUtils.copyProperties(newsDTO, news);
        String imageUrl = getUrl(newsDTO.getContent());
        if(StrUtil.isNotBlank(imageUrl)){
            news.setPicture(imageUrl);
        }

        // 是否定时发布:  0 不定时  1定时
        if(IsFixedEnum.SIGNED.getCode().equals(newsDTO.getIsFixed())){
            news.setFixedAt(newsDTO.getFixedAt());
        }else{
            news.setFixedAt(null);
            // 立即发布
            news.setPublishAt(new Date());
        }
        newsMapper.updateById(news);

        // 是否定时发布:  0 不定时  1定时
        if(IsFixedEnum.SIGNED.getCode().equals(newsDTO.getIsFixed())){
            // 写定时任务
            String key = RedisKeys.NEWS_PUBLIS_TIME + news.getId();
            // 过期时间
            long timeout = DateUtil.between(new Date(), newsDTO.getFixedAt(), DateUnit.MINUTE);
            stringRedisTemplate.opsForValue().set(key, String.valueOf(news.getId()), timeout, TimeUnit.MINUTES);
        }
    }

    @Override
    public void updateStatus(String id, Integer status) {
        LambdaUpdateWrapper<News> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(News::getStatus, status)
                .set(News::getPublishAt, new Date())
                .eq(News::getId, Long.valueOf(id));
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

        List<SearchNewsResponse> responses = newsMapper.queryNews(searchNewsRequest);
        PageInfo<SearchNewsResponse> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<SearchNewsResponse>>() {
        }.build(), new TypeBuilder<PageResult<SearchNewsResponse>>() {}.build());
    }

    @Override
    public List<NewsResponse> getHotNews() {
        LambdaQueryWrapper<News> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(News::getViews)
                          .orderByDesc(News::getCreateAt)
                          .last("LIMIT 5");
        List<News> newsList = newsMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(newsList)){
            return mapperFacade.mapAsList(newsList, NewsResponse.class);
        }

        return null;
    }

    @Override
    public NewsRelatedDTO getLastRelated(Integer type, Date date) {
        LambdaQueryWrapper<News> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(News::getNewsPolicy, type);
        lambdaQueryWrapper.lt(News::getCreateAt, date);
        lambdaQueryWrapper.orderByDesc(News::getCreateAt);
        lambdaQueryWrapper.last("limit 1");

        List<News> newsList = newsMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(newsList)){
            News news = newsList.get(0);
            return mapperFacade.map(news, NewsRelatedDTO.class);
        }
        return null;
    }

    @Override
    public NewsRelatedDTO getNextRelated(Integer type, Date date) {
        LambdaQueryWrapper<News> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(News::getNewsPolicy, type);
        lambdaQueryWrapper.gt(News::getCreateAt, date);
        lambdaQueryWrapper.orderByDesc(News::getCreateAt);
        lambdaQueryWrapper.last("limit 1");

        List<News> newsList = newsMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(newsList)){
            News news = newsList.get(0);
            return mapperFacade.map(news, NewsRelatedDTO.class);
        }
        return null;
    }

}
