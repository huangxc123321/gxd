package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.BillboardGainApi;
import com.gxa.jbgsw.business.entity.BillboardGain;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainResponse;
import com.gxa.jbgsw.business.service.BillboardGainService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    MapperFacade mapperFacade;

    @Override
    public List<BillboardGainDTO> getBillboardGainByPid(Long id) {
        List<BillboardGain> billboardGains = billboardGainService.getBillboardGainByPid(id);
        List<BillboardGainDTO> responses = mapperFacade.mapAsList(billboardGains, BillboardGainDTO.class);

        return responses;
    }
}

