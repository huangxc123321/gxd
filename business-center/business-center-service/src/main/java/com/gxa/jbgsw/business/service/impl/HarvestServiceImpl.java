package com.gxa.jbgsw.business.service.impl;

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
    public PageResult<Harvest> pageQuery(HarvestRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<Harvest> harvests = harvestMapper.pageQuery(request);
        PageInfo<Harvest> pageInfo = new PageInfo<>(harvests);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Harvest>>() {
        }.build(), new TypeBuilder<PageResult<Harvest>>() {}.build());
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
        lambdaQueryWrapper.orderByDesc(Harvest::getCreateAt, Harvest::getViews);
        lambdaQueryWrapper.last("limit 3");

        List<Harvest> harvests = harvestMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(harvests)){
            return mapperFacade.mapAsList(harvests, RecommendHavestResponse.class);
        }

        return null;
    }
}
