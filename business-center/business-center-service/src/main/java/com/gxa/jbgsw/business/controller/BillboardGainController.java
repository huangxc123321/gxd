package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.BillboardGainApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardGain;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainAddDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainAuditDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainResponse;
import com.gxa.jbgsw.business.service.BillboardGainService;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.common.exception.BizException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "榜单揭榜管理")
public class BillboardGainController implements BillboardGainApi {
    @Resource
    BillboardGainService billboardGainService;
    @Resource
    BillboardService billboardService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<BillboardGainDTO> getBillboardGainByPid(Long id) {
        List<BillboardGain> billboardGains = billboardGainService.getBillboardGainByPid(id);
        // 空直接返回
        if(billboardGains == null){
            return new ArrayList<>();
        }

        List<BillboardGainDTO> responses = mapperFacade.mapAsList(billboardGains, BillboardGainDTO.class);
        return responses;
    }

    @Override
    public BillboardGainDTO getBillboardGainById(Long id) {
        BillboardGain billboardGain = billboardGainService.getById(id);
        BillboardGainDTO billboardGainDTO = mapperFacade.map(billboardGain, BillboardGainDTO.class);

        Billboard billboard = billboardService.getById(billboardGain.getPid());
        if(billboard != null){
            billboardGainDTO.setTitle(billboard.getTitle());
        }

        return  billboardGainDTO;
    }

    @Override
    public void update(BillboardGainAuditDTO billboardGainAuditDTO) throws BizException {
        BillboardGain billboardGain = billboardGainService.getById(billboardGainAuditDTO.getId());
        if(billboardGain != null){
            billboardGain.setAuditingUserId(billboardGainAuditDTO.getAuditingUserId());
            billboardGain.setAuditingUserName(billboardGainAuditDTO.getAuditingUserName());
            billboardGain.setAuditingStatus(billboardGainAuditDTO.getAuditingStatus());
            billboardGain.setReason(billboardGainAuditDTO.getReason());

            billboardGainService.updateById(billboardGain);
        }
    }

    @Override
    public void addBillboardGain(BillboardGainAddDTO billboardGainAddDTO) {
        BillboardGain billboardGain = mapperFacade.map(billboardGainAddDTO, BillboardGain.class);

        billboardGainService.save(billboardGain);
    }

}

