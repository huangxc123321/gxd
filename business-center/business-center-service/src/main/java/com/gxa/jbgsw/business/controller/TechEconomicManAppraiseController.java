package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.business.client.TechEconomicManAppraiseApi;
import com.gxa.jbgsw.business.entity.TechEconomicManAppraise;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseDTO;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseRequest;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseResponse;
import com.gxa.jbgsw.business.service.TechEconomicManAppraiseService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "评价管理")
public class TechEconomicManAppraiseController implements TechEconomicManAppraiseApi {
    @Resource
    TechEconomicManAppraiseService techEconomicManAppraiseService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(TechEconomicManAppraiseDTO techEconomicManAppraiseDTO) {
        TechEconomicManAppraise techEconomicManAppraise = mapperFacade.map(techEconomicManAppraiseDTO, TechEconomicManAppraise.class);
        techEconomicManAppraiseService.save(techEconomicManAppraise);
    }

    @Override
    public PageResult<TechEconomicManAppraiseResponse> getAppraise(TechEconomicManAppraiseRequest request) {
        return techEconomicManAppraiseService.getAppraise(request);
    }
}
