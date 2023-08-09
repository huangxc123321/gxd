package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.BillboardEconomicRelatedApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardEconomicRelated;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.service.BillboardEconomicRelatedService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "榜单与经纪人之间关联管理")
public class BillboardEconomicRelatedController implements BillboardEconomicRelatedApi {
    @Resource
    BillboardEconomicRelatedService billboardEconomicRelatedService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(BillboardRelatedDTO billboardRelatedDTO) {
        billboardRelatedDTO.setCreateAt(new Date());
        BillboardEconomicRelated billboardEconomicRelated = mapperFacade.map(billboardRelatedDTO, BillboardEconomicRelated.class);

        billboardEconomicRelatedService.save(billboardEconomicRelated);
    }

    @Override
    public void audit(BillboardRelatedAuditDTO billboardRelatedAuditDTO) {
        BillboardEconomicRelated billboardEconomicRelated = billboardEconomicRelatedService.getById(billboardRelatedAuditDTO.getId());

        billboardEconomicRelated.setHStart(billboardRelatedAuditDTO.getHStart());
        billboardEconomicRelated.setRecommendAt(new Date());
        billboardEconomicRelated.setUserName(billboardRelatedAuditDTO.getUserName());
        billboardEconomicRelated.setRemark(billboardRelatedAuditDTO.getRemark());
        billboardEconomicRelated.setStatus(billboardRelatedAuditDTO.getStatus());

        billboardEconomicRelatedService.updateById(billboardEconomicRelated);
    }

    @Override
    public List<BillboardEconomicRelatedResponse> getEconomicRecommend(Long billboardId) {
        return billboardEconomicRelatedService.getEconomicRecommend(billboardId);
    }

    @Override
    public MyBillboardEconomicManDTO getMyEconomicMan(Long billboardId) {
        BillboardEconomicRelated billboardEconomicRelated = billboardEconomicRelatedService.getMyEconomicMan(billboardId);
        if(billboardEconomicRelated != null){
            MyBillboardEconomicManDTO myBillboardEconomicManDTO = mapperFacade.map(billboardEconomicRelated, MyBillboardEconomicManDTO.class);
            return myBillboardEconomicManDTO;
        }

        return null;
    }

    @Override
    public BillboardEconomicRelatedDTO getById(Long id) {
        BillboardEconomicRelated billboardEconomicRelated = billboardEconomicRelatedService.getById(id);
        if(billboardEconomicRelated != null){
            return mapperFacade.map(billboardEconomicRelated, BillboardEconomicRelatedDTO.class);
        }
        return null;
    }


}

