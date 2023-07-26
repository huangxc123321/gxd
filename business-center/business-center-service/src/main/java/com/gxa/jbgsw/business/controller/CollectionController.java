package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.CollectionApi;
import com.gxa.jbgsw.business.entity.Collection;
import com.gxa.jbgsw.business.protocol.dto.CollectionDTO;
import com.gxa.jbgsw.business.protocol.dto.MyCollectionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyCollectionResponse;
import com.gxa.jbgsw.business.protocol.enums.CollectionTypeEnum;
import com.gxa.jbgsw.business.service.CollectionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    @Override
    public void delete(CollectionDTO collectionDTO) {
        collectionService.deleteCollection(collectionDTO);
    }

    @Override
    public CollectionDTO getCollection(Long pid, Long userId, Integer type) {
        Collection collection = collectionService.getCollection(pid, userId, type);
        if(collection != null){
            CollectionDTO collectionDTO = mapperFacade.map(collection, CollectionDTO.class);

            return collectionDTO;
        }

        return null;
    }

    @Override
    public MyCollectionResponse queryMyCollections(MyCollectionRequest myCollectionRequest) {
        MyCollectionResponse myCollectionResponse = new MyCollectionResponse();

        List<Collection> collections = collectionService.queryMyCollections(myCollectionRequest.getCreateBy());
        if(CollectionUtils.isNotEmpty(collections)){
             Integer collectionType = null;
             for(int i=0; i<collections.size(); i++){
                 Collection coll = collections.get(i);
                 collectionType = coll.getCollectionType();
                 // 收藏类型： 0政府榜 1企业榜 2成果 3政策 4帅才
                 if(CollectionTypeEnum.GOV.getCode().equals(collectionType)){
                     myCollectionResponse.setGovBillboardsNum(myCollectionResponse.getBuzBillboardsNum() + 1);
                 }else if(CollectionTypeEnum.BUZ.getCode().equals(collectionType)){
                     myCollectionResponse.setHavestBillboardsNum(myCollectionResponse.getBuzBillboardsNum() + 1);
                 }

             }
        }




        return null;
    }
}

