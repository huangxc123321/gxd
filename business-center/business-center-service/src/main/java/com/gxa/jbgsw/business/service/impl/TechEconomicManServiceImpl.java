package com.gxa.jbgsw.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.TechEconomicMan;
import com.gxa.jbgsw.business.mapper.TechEconomicManMapper;
import com.gxa.jbgsw.business.protocol.dto.SearchEconomicMansRequest;
import com.gxa.jbgsw.business.protocol.dto.SearchEconomicMansResponse;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManRequest;
import com.gxa.jbgsw.business.service.TechEconomicManService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 技术经济人 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class TechEconomicManServiceImpl extends ServiceImpl<TechEconomicManMapper, TechEconomicMan> implements TechEconomicManService {
    @Resource
    TechEconomicManMapper techEconomicManMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        techEconomicManMapper.deleteBatchIds(list);
    }

    @Override
    public PageResult<TechEconomicMan> pageQuery(TechEconomicManRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<TechEconomicMan> responses = techEconomicManMapper.pageQuery(request);
        PageInfo<TechEconomicMan> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<TechEconomicMan>>() {
        }.build(), new TypeBuilder<PageResult<TechEconomicMan>>() {}.build());
    }

    @Override
    public PageResult<SearchEconomicMansResponse> queryEconomicMans(SearchEconomicMansRequest searchTalentsRequest) {
        PageHelper.startPage(searchTalentsRequest.getPageNum(), searchTalentsRequest.getPageSize());

        List<SearchEconomicMansResponse> responses = techEconomicManMapper.queryEconomicMans(searchTalentsRequest);
        PageInfo<SearchEconomicMansResponse> pageInfo = new PageInfo<>(responses);
        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<SearchEconomicMansResponse>>() {
        }.build(), new TypeBuilder<PageResult<SearchEconomicMansResponse>>() {}.build());
    }

    @Override
    public List<String> getLabels() {
        return techEconomicManMapper.getLabels();
    }
}
