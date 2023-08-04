package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.client.BillboardHarvestRelatedApi;
import com.gxa.jbgsw.business.entity.BillboardHarvestRelated;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.service.BillboardHarvestRelatedService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "榜单与成果之间关联管理")
public class BillboardHarvestRelatedController implements BillboardHarvestRelatedApi {
    @Resource
    BillboardHarvestRelatedService billboardHarvestRelatedService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(BillboardRelatedDTO billboardHarvestRelatedDTO) {
        billboardHarvestRelatedDTO.setCreateAt(new Date());
        BillboardHarvestRelated billboardHarvestRelated = mapperFacade.map(billboardHarvestRelatedDTO, BillboardHarvestRelated.class);

        billboardHarvestRelatedService.save(billboardHarvestRelated);
    }

    @Override
    public void audit(BillboardRelatedAuditDTO billboardHarvestAuditDTO) {
        BillboardHarvestRelated billboardHarvestRelated = billboardHarvestRelatedService.getById(billboardHarvestAuditDTO.getId());

        billboardHarvestRelated.setHStart(billboardHarvestAuditDTO.getHStart());
        billboardHarvestRelated.setRecommendAt(new Date());
        billboardHarvestRelated.setUserId(billboardHarvestAuditDTO.getUserId());
        billboardHarvestRelated.setUserName(billboardHarvestAuditDTO.getUserName());
        billboardHarvestRelated.setRemark(billboardHarvestAuditDTO.getRemark());
        billboardHarvestRelated.setStatus(billboardHarvestAuditDTO.getStatus());

        billboardHarvestRelatedService.updateById(billboardHarvestRelated);
    }

    @Override
    public List<BillboardHarvestRelatedResponse> getHarvestRecommend(Long billboardId) {
        List<BillboardHarvestRelatedResponse> responses = billboardHarvestRelatedService.getHarvestRecommend(billboardId);
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeEnum.maturity_level.name(), String.valueOf(s.getMaturityLevel()));
                if(dictionaryDTO != null){
                    s.setMaturityLevelName(dictionaryDTO.getDicValue());
                    if(s.getHStart() == null){
                        s.setHStart(s.getSStar());
                    }
                }
            });
        }
        return responses;
    }

    @Override
    public List<HavestCollaborateDTO> getHarvestRecommendByHarvestId(Long harvestId) {
        return billboardHarvestRelatedService.getHarvestRecommendByHarvestId(harvestId);
    }

    @Override
    public List<RelateBillboardDTO> getHarvestByHarvestId(Long harvestId) {
        List<BillboardResponse> responses =  billboardHarvestRelatedService.getHarvestByHarvestId(harvestId);
        if(responses != null){
            List<RelateBillboardDTO> billboards = mapperFacade.mapAsList(responses, RelateBillboardDTO.class);

            return billboards;
        }

        return null;
    }

    @Override
    public List<BillboardHarvestRelatedResponse> getBillboardstByHarvestId(Long harvestId) {
        List<BillboardHarvestRelatedResponse> responses =  billboardHarvestRelatedService.getBillboardstByHarvestId(harvestId);
        return responses;
    }
}

