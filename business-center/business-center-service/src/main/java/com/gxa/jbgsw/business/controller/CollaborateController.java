package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.CollaborateApi;
import com.gxa.jbgsw.business.entity.Collaborate;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.entity.TalentPool;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.CollaborateTypeEnum;
import com.gxa.jbgsw.business.service.CollaborateService;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.business.service.TalentPoolService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "合作管理")
public class CollaborateController implements CollaborateApi {
    @Resource
    CollaborateService collaborateService;
    @Resource
    TalentPoolService talentPoolService;
    @Resource
    HarvestService harvestService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void cancel(@RequestParam("id") Long id) {
        collaborateService.delete(id);
    }

    @Override
    public void add(CollaborateDTO collaborateDTO) {
        Collaborate collaborate = mapperFacade.map(collaborateDTO, Collaborate.class);
        collaborateService.saveCollaborate(collaborate);


    }

    @Override
    public List<HavestCollaborateDTO> getHavestCollaborates(Long id) {
        return collaborateService.getHavestCollaborates(id);
    }

    @Override
    public CollaborateHavrestDetailDTO getHarvestCollaborateDetail(@RequestParam("id") Long id) {
        Collaborate collaborate = collaborateService.getById(id);
        CollaborateHavrestDetailDTO collaborateHavrestDetail = mapperFacade.map(collaborate, CollaborateHavrestDetailDTO.class);

        Harvest harvest = harvestService.getById(collaborate.getPid());
        if(harvest != null){
            collaborateHavrestDetail.setName(harvest.getName());
        }

        return collaborateHavrestDetail;
    }

//    @Override
//    public CollaborateTaleDetailDTO getTalentCollaborateDetail(@RequestParam("id") Long id) {
//        return null;
//    }

    @Override
    public CollaborateDTO getById(@RequestParam("id") Long id) {
        Collaborate collaborate = collaborateService.getById(id);

        return mapperFacade.map(collaborate, CollaborateDTO.class);
    }

    @Override
    public PageResult pageQuery(@RequestBody MyCollaborateRequest request) {
        // 成果
        if(request.getType().equals(CollaborateTypeEnum.GAIN.getCode())){
            PageResult pageResult =  collaborateService.getCollaborateHarvest(request);

            return pageResult;
        }else{
            // 需求合作
            PageResult pageResult =  collaborateService.getCollaborateTalent(request);

            return pageResult;
        }
    }
}

