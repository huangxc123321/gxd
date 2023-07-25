package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.CollaborateApi;
import com.gxa.jbgsw.business.entity.Collaborate;
import com.gxa.jbgsw.business.protocol.dto.CollaborateDTO;
import com.gxa.jbgsw.business.service.CollaborateService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(tags = "合作管理")
public class CollaborateController implements CollaborateApi {
    @Resource
    CollaborateService collaborateService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(CollaborateDTO collaborateDTO) {
        Collaborate collaborate = mapperFacade.map(collaborateDTO, Collaborate.class);


        collaborateService.save(collaborate);
    }
}

