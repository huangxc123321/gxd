package com.gxa.jbgsw.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.entity.TechEconomicMan;
import com.gxa.jbgsw.business.entity.TechEconomicManAppraise;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseRequest;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseResponse;
import com.gxa.jbgsw.common.utils.PageResult;

/**
 * @Author Mr. huang
 * @Date 2023/7/18 0018 17:01
 * @Version 2.0
 */
public interface TechEconomicManAppraiseService extends IService<TechEconomicManAppraise> {
    PageResult<TechEconomicManAppraiseResponse> getAppraise(TechEconomicManAppraiseRequest request);
}
