package com.gxa.jbgsw.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.entity.TechEconomicManAppraise;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseRequest;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/18 0018 16:59
 * @Version 2.0
 */
public interface TechEconomicManAppraiseMapper extends BaseMapper<TechEconomicManAppraise> {
    List<TechEconomicManAppraise> getAppraise(TechEconomicManAppraiseRequest request);
}
