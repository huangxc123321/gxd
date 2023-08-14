package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.entity.Attention;
import com.gxa.jbgsw.business.entity.Collection;
import com.gxa.jbgsw.business.mapper.CollectionMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AttentionTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.CollectionTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.CollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 我的收藏 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
    @Resource
    CollectionMapper collectionMapper;

    @Override
    public void deleteCollection(CollectionDTO collectionDTO) {
        LambdaQueryWrapper<Collection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collection::getPid, collectionDTO.getPid());
        lambdaQueryWrapper.eq(Collection::getUserId, collectionDTO.getUserId());

        collectionMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public Collection getCollection(Long pid, Long userId, Integer type) {
        LambdaQueryWrapper<Collection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collection::getPid, pid);
        lambdaQueryWrapper.eq(Collection::getUserId, userId);
        lambdaQueryWrapper.eq(Collection::getCollectionType, type);
        lambdaQueryWrapper.last("limit 1");

        List<Collection> collections = collectionMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(collections)){
            return collections.get(0);
        }

        return null;
    }

    @Override
    public void deleteBatchByPid(List<Long> ids) {
        LambdaQueryWrapper<Collection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Collection::getPid, ids);

        collectionMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public List<Collection> queryMyCollections(Long createBy) {
        LambdaQueryWrapper<Collection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Collection::getUserId, createBy);

        List<Collection> collections = collectionMapper.selectList(lambdaQueryWrapper);
        return collections;
    }

    @Override
    public void deleteById(Long id) {
        collectionMapper.deleteById(id);
    }

    @Override
    public List<MyCollectionBillboardResponse> getMyCollectionBillboardResponse(Long createBy, Integer collectionType) {
        return collectionMapper.getMyCollectionBillboardResponse(createBy, collectionType);
    }

    @Override
    public List<MyHavestBillboardResponse> getMyHavestBillboardResponse(Long createBy, Integer collectionType) {
        return collectionMapper.getMyHavestBillboardResponse(createBy, collectionType);
    }

    @Override
    public List<MypolicyResponse> getPolicys(Long createBy, Integer collectionType) {
        return collectionMapper.getPolicys(createBy, collectionType);
    }

    @Override
    public Integer getCollections(Long userId) {
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .eq("user_id", userId);

        Integer count = collectionMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public MyCollectionResponse pageQuery(MyCollectionRequest myCollectionRequest) {
        MyCollectionResponse response = new MyCollectionResponse();

        // 获取该类型下的分页数据
        PageHelper.startPage(myCollectionRequest.getPageNum(), myCollectionRequest.getPageSize());

        // 0政府榜 1企业榜 2成果 3政策
        if(CollectionTypeEnum.GOV.getCode().equals(myCollectionRequest.getCollectionType())){
            Integer govNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.GOV.getCode());
            Integer buzNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.BUZ.getCode());
            Integer havestNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.HAVEST.getCode());
            Integer policyNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.POC.getCode());
            response.setGovBillboardsNum(govNum);
            response.setBuzBillboardsNum(buzNum);
            response.setHavestBillboardsNum(havestNum);
            response.setPolicyNum(policyNum);

            List<MyCollectionBillboardResponse> govs = collectionMapper.getMyCollectionBillboardResponse(myCollectionRequest.getCreateBy(), myCollectionRequest.getCollectionType());
            response.setGovBillboards(govs);

        }
        else if(CollectionTypeEnum.BUZ.getCode().equals(myCollectionRequest.getCollectionType())){
            Integer govNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.GOV.getCode());
            Integer buzNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.BUZ.getCode());
            Integer havestNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.HAVEST.getCode());
            Integer policyNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.POC.getCode());
            response.setGovBillboardsNum(govNum);
            response.setBuzBillboardsNum(buzNum);
            response.setHavestBillboardsNum(havestNum);
            response.setPolicyNum(policyNum);

            List<MyCollectionBillboardResponse> buzs = collectionMapper.getMyCollectionBillboardResponse(myCollectionRequest.getCreateBy(), myCollectionRequest.getCollectionType());
            response.setBuzBillboards(buzs);
        } else if(CollectionTypeEnum.HAVEST.getCode().equals(myCollectionRequest.getCollectionType())){
            Integer govNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.GOV.getCode());
            Integer buzNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.BUZ.getCode());
            Integer havestNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.HAVEST.getCode());
            Integer policyNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.POC.getCode());
            response.setGovBillboardsNum(govNum);
            response.setBuzBillboardsNum(buzNum);
            response.setHavestBillboardsNum(havestNum);
            response.setPolicyNum(policyNum);

            List<MyHavestBillboardResponse> havests = collectionMapper.getMyHavestBillboardResponse(myCollectionRequest.getCreateBy(), myCollectionRequest.getCollectionType());
            response.setHavestBillboards(havests);
        } else if(CollectionTypeEnum.POC.getCode().equals(myCollectionRequest.getCollectionType())){
            Integer govNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.GOV.getCode());
            Integer buzNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.BUZ.getCode());
            Integer havestNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.HAVEST.getCode());
            Integer policyNum = getCollectionNum(myCollectionRequest.getCreateBy(), CollectionTypeEnum.POC.getCode());
            response.setGovBillboardsNum(govNum);
            response.setBuzBillboardsNum(buzNum);
            response.setHavestBillboardsNum(havestNum);
            response.setPolicyNum(policyNum);

            List<MypolicyResponse> policys = collectionMapper.getPolicys(myCollectionRequest.getCreateBy(), myCollectionRequest.getCollectionType());
            response.setPolicys(policys);
        }

        return response;
    }

    Integer getCollectionNum(Long userId, Integer type){
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .eq("collection_type", type)
                .eq("user_id", userId);

        Integer count = collectionMapper.selectCount(queryWrapper);
        return count;
    }



}
