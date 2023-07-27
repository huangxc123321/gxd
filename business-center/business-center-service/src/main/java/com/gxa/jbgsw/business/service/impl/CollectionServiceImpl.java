package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxa.jbgsw.business.entity.Collection;
import com.gxa.jbgsw.business.mapper.CollectionMapper;
import com.gxa.jbgsw.business.protocol.dto.CollectionDTO;
import com.gxa.jbgsw.business.protocol.dto.MyCollectionBillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.MyHavestBillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.MypolicyResponse;
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
}
