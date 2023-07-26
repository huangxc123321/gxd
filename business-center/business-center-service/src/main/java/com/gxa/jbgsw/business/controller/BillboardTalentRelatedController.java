package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.business.client.BillboardTalentRelatedApi;
import com.gxa.jbgsw.business.entity.BillboardHarvestRelated;
import com.gxa.jbgsw.business.entity.BillboardTalentRelated;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.service.BillboardHarvestRelatedService;
import com.gxa.jbgsw.business.service.BillboardTalentRelatedService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author huangxc
 */

@RestController
@Slf4j
@Api(tags = "榜单与帅才之间关联管理")
public class BillboardTalentRelatedController implements BillboardTalentRelatedApi {
    @Resource
    BillboardTalentRelatedService billboardTalentRelatedService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(BillboardRelatedDTO billboardRelatedDTO) {
        billboardRelatedDTO.setCreateAt(new Date());
        BillboardTalentRelated billboardTalentRelated = mapperFacade.map(billboardRelatedDTO, BillboardTalentRelated.class);

        billboardTalentRelatedService.save(billboardTalentRelated);
    }

    @Override
    public void audit(BillboardRelatedAuditDTO billboardRelatedAuditDTO) {
        BillboardTalentRelated billboardTalentRelated = billboardTalentRelatedService.getById(billboardRelatedAuditDTO.getId());

        billboardTalentRelated.setHStart(billboardRelatedAuditDTO.getHStart());
        billboardTalentRelated.setRecommendAt(new Date());
        billboardTalentRelated.setUserId(billboardRelatedAuditDTO.getUserId());
        billboardTalentRelated.setUserName(billboardRelatedAuditDTO.getUserName());
        billboardTalentRelated.setRemark(billboardRelatedAuditDTO.getRemark());
        billboardTalentRelated.setStatus(billboardRelatedAuditDTO.getStatus());

        billboardTalentRelatedService.updateById(billboardTalentRelated);
    }

    @Override
    public List<BillboardTalentRelatedResponse> getTalentRecommend(Long billboardId) {
        return billboardTalentRelatedService.getTalentRecommend(billboardId);
    }

    @Override
    public List<HarvestBillboardRelatedDTO> getBillboardRecommendByTalentId(Long id) {
        return billboardTalentRelatedService.getBillboardRecommendByTalentId(id);
    }

    @Override
    public List<HavestCollaborateDTO> getCollaborateByTalentId(Long id) {
        return billboardTalentRelatedService.getCollaborateByTalentId(id);
    }

    @Override
    public List<MyBillboradCollaborateResponse> getMyBillboradCollaborate(Long talentId) {
        return billboardTalentRelatedService.getMyBillboradCollaborate(talentId);
    }
}


