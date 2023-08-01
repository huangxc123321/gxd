package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.TechEconomicManAppraise;
import com.gxa.jbgsw.business.mapper.TechEconomicManAppraiseMapper;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseRequest;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseResponse;
import com.gxa.jbgsw.business.service.TechEconomicManAppraiseService;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TechEconomicManAppraiseServiceImpl extends ServiceImpl<TechEconomicManAppraiseMapper, TechEconomicManAppraise> implements TechEconomicManAppraiseService {
    @Resource
    TechEconomicManAppraiseMapper techEconomicManAppraiseMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public PageResult<TechEconomicManAppraiseResponse> getAppraise(TechEconomicManAppraiseRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<TechEconomicManAppraise> list = techEconomicManAppraiseMapper.getAppraise(request);
        PageInfo<TechEconomicManAppraise> pageInfo = new PageInfo<>(list);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<TechEconomicManAppraise>>() {
        }.build(), new TypeBuilder<PageResult<TechEconomicManAppraiseResponse>>() {}.build());

    }
}
