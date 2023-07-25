package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.CollectionApi;
import com.gxa.jbgsw.business.entity.Collection;
import com.gxa.jbgsw.business.protocol.dto.CollectionDTO;
import com.gxa.jbgsw.business.service.CollectionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(tags = "收藏管理")
public class CollectionController implements CollectionApi {
    @Resource
    CollectionService collectionService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(CollectionDTO collectionDTO) {
        Collection collection = mapperFacade.map(collectionDTO, Collection.class);

        collectionService.save(collection);
    }
}

