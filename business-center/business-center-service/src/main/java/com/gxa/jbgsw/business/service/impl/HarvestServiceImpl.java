package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.entity.News;
import com.gxa.jbgsw.business.mapper.HarvestMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.service.HarvestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 成果信息 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class HarvestServiceImpl extends ServiceImpl<HarvestMapper, Harvest> implements HarvestService {
    @Resource
    HarvestMapper harvestMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<Harvest> getHarvesByHolder(String holder) {
        LambdaQueryWrapper<Harvest> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Harvest::getHolder, holder)
                .orderByDesc(Harvest::getCreateAt);
        List<Harvest> harvests = harvestMapper.selectList(lambdaQueryWrapper);

        return harvests;
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        harvestMapper.deleteBatchIds(list);
    }

    @Override
    public PageResult<HarvestResponse> pageQuery(HarvestRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<HarvestResponse> harvests = harvestMapper.pageQuery(request);
        PageInfo<HarvestResponse> pageInfo = new PageInfo<>(harvests);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<HarvestResponse>>() {
        }.build(), new TypeBuilder<PageResult<HarvestResponse>>() {}.build());
    }

    @Override
    public PageResult<SearchHavestResponse> queryHarvests(SearchHarvestsRequest searchHarvestsRequest) {
        PageHelper.startPage(searchHarvestsRequest.getPageNum(), searchHarvestsRequest.getPageSize());

        List<SearchHavestResponse> harvests = harvestMapper.queryHarvests(searchHarvestsRequest);
        PageInfo<SearchHavestResponse> pageInfo = new PageInfo<>(harvests);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<SearchHavestResponse>>() {
        }.build(), new TypeBuilder<PageResult<SearchHavestResponse>>() {}.build());
    }

    @Override
    public List<Harvest> getHarvesByTechDomain(String techDomain) {
        LambdaQueryWrapper<Harvest> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Harvest::getTechDomain, techDomain)
                .orderByDesc(Harvest::getCreateAt);
        List<Harvest> harvests = harvestMapper.selectList(lambdaQueryWrapper);

        return harvests;
    }

    @Override
    public List<RecommendHavestResponse> getRecommendHavest() {
        LambdaQueryWrapper<Harvest> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Harvest::getCreateAt)
                          .orderByDesc(Harvest::getViews)
                          .last("LIMIT 3");

        List<Harvest> harvests = harvestMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(harvests)){
            return mapperFacade.mapAsList(harvests, RecommendHavestResponse.class);
        }

        return null;
    }

    @Override
    public PageResult<Harvest> pageMyHarvestQuery(HarvestRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<Harvest> harvests = harvestMapper.pageMyHarvestQuery(request);
        PageInfo<Harvest> pageInfo = new PageInfo<>(harvests);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Harvest>>() {
        }.build(), new TypeBuilder<PageResult<Harvest>>() {}.build());
    }

    @Override
    public List<HarvestResponse> getHarvestByUnitName(String name) {
        LambdaQueryWrapper<Harvest> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Harvest::getUnitName, name)
                          .orderByDesc(Harvest::getCreateAt);

        List<Harvest> harvests = harvestMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(harvests)){
            return mapperFacade.mapAsList(harvests, HarvestResponse.class);
        }

        return null;
    }

    @Override
    public List<String> getHolders(String holder) {
        LambdaQueryWrapper<Harvest> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(holder)){
            lambdaQueryWrapper.like(Harvest::getHolder, holder);
        }

        List<Harvest> harvests = harvestMapper.selectList(lambdaQueryWrapper);
        if(harvests != null && harvests.size() >0){
            List<String> ls = new ArrayList<>();
            harvests.stream().forEach(s->{
               ls.add(s.getUnitName());
            });
            // 去重返回
            return ls.stream().distinct().collect(Collectors.toList());
        }

        return null;
    }
}
