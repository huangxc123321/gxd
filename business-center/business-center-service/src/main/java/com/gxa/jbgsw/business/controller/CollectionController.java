package com.gxa.jbgsw.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxa.jbgsw.business.client.CollectionApi;
import com.gxa.jbgsw.business.entity.Attention;
import com.gxa.jbgsw.business.entity.Collection;
import com.gxa.jbgsw.business.protocol.dto.*;
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
                 // 收藏类型： 0政府榜 1企业榜 2成果 3政策
                 if(CollectionTypeEnum.GOV.getCode().equals(collectionType)){
                     // 数量
                     myCollectionResponse.setGovBillboardsNum(myCollectionResponse.getGovBillboardsNum() + 1);
                     // 我收藏的政府榜
                     List<MyCollectionBillboardResponse> responses
                             = collectionService.getMyCollectionBillboardResponse(myCollectionRequest.getCreateBy(), myCollectionRequest.getCollectionType());

                     myCollectionResponse.setGovBillboards(responses);
                 }

                 else if(CollectionTypeEnum.BUZ.getCode().equals(collectionType)){
                     myCollectionResponse.setBuzBillboardsNum(myCollectionResponse.getBuzBillboardsNum() + 1);
                     // 我收藏的政府榜
                     List<MyCollectionBillboardResponse> responses
                             = collectionService.getMyCollectionBillboardResponse(myCollectionRequest.getCreateBy(), myCollectionRequest.getCollectionType());
                     myCollectionResponse.setBuzBillboards(responses);
                 }

                 else if(CollectionTypeEnum.HAVEST.getCode().equals(collectionType)){
                     myCollectionResponse.setHavestBillboardsNum(myCollectionResponse.getHavestBillboardsNum() + 1);
                     List<MyHavestBillboardResponse> havestBillboardResponses
                             = collectionService.getMyHavestBillboardResponse(myCollectionRequest.getCreateBy(), myCollectionRequest.getCollectionType());
                     myCollectionResponse.setHavestBillboards(havestBillboardResponses);
                 }

                 else if(CollectionTypeEnum.POC.getCode().equals(collectionType)){
                     myCollectionResponse.setPolicyNum(myCollectionResponse.getPolicyNum() + 1);
                     List<MypolicyResponse> policys = collectionService.getPolicys(myCollectionRequest.getCreateBy(), myCollectionRequest.getCollectionType());
                     myCollectionResponse.setPolicys(policys);
                 }
             }

        }

        return myCollectionResponse;
    }

    @Override
    public void deleteById(Long id) {
        collectionService.deleteById(id);
    }

    @Override
    public Integer getCollections(Long userId) {
        return collectionService.getCollections(userId);
    }
}

