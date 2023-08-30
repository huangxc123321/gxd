package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.BillboardTemporary;
import com.gxa.jbgsw.business.mapper.BillboardTemporaryMapper;
import com.gxa.jbgsw.business.protocol.dto.BillboardIndexDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryResponse;
import com.gxa.jbgsw.business.service.BillboardTemporaryService;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillboardTemporaryServiceImpl extends ServiceImpl<BillboardTemporaryMapper, BillboardTemporary> implements BillboardTemporaryService {
    @Resource
    BillboardTemporaryMapper billboardTemporaryMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void batchInsert(List<BillboardTemporaryDTO> list) {
        List<BillboardTemporary> billboardTemporaries = mapperFacade.mapAsList(list, BillboardTemporary.class);
        this.saveBatch(billboardTemporaries);
    }

    @Override
    public PageResult<BillboardTemporaryResponse> pageQuery(BillboardTemporaryRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<BillboardTemporary> responses = billboardTemporaryMapper.pageQuery(request);

        if(CollectionUtils.isNotEmpty(responses)){
            PageInfo<BillboardTemporary> pageInfo = new PageInfo<>(responses);

            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<BillboardTemporary>>() {
            }.build(), new TypeBuilder<PageResult<BillboardTemporaryResponse>>() {}.build());
        }
        return new PageResult<>();
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        billboardTemporaryMapper.deleteBatchIds(list);
    }

    @Override
    public void deleteByCreateBy(Long userId) {
        LambdaQueryWrapper<BillboardTemporary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BillboardTemporary::getCreateBy, userId);

        billboardTemporaryMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void updateStatusByCreateBy(Long createBy, Integer status) {
        LambdaUpdateWrapper<BillboardTemporary> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(BillboardTemporary::getStatus, status);
        lambdaUpdateWrapper.eq(BillboardTemporary::getCreateBy, createBy);

        billboardTemporaryMapper.update(null, lambdaUpdateWrapper);
    }
}
