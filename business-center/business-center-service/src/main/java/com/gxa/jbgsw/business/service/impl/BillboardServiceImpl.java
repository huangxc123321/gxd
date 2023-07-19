package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardGain;
import com.gxa.jbgsw.business.entity.TalentPool;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.mapper.BillboardMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.service.BillboardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.ApiModelProperty;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 榜单信息 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class BillboardServiceImpl extends ServiceImpl<BillboardMapper, Billboard> implements BillboardService {
    @Resource
    BillboardMapper billboardMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        billboardMapper.deleteBatchIds(list);
    }

    @Override
    /**
     * id: 榜单ID
     * isTop: 是否置顶： 0 不置顶 1 置顶
     */
    public void updateTop(Long id, int isTop) {
        LambdaUpdateWrapper<Billboard> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Billboard::getIsTop, isTop)
                           .eq(Billboard::getId, id);

        billboardMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void batchIdsTop(Long[] ids, int isTop) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());

        LambdaUpdateWrapper<Billboard> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Billboard::getIsTop, isTop)
                .in(Billboard::getId, list);

        billboardMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public PageResult<Billboard> pageQuery(BillboardRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<Billboard> responses = billboardMapper.pageQuery(request);
        PageInfo<Billboard> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Billboard>>() {
        }.build(), new TypeBuilder<PageResult<Billboard>>() {}.build());
    }

    @Override
    public void updateSeqNo(Long id, Integer seqNo) {
        LambdaUpdateWrapper<Billboard> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.setSql("seq_no = seq_no + "+seqNo)
                .eq(Billboard::getId, id);

        billboardMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public PageResult<Billboard> LastBillboardSetData(LastBillboardRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<Billboard> responses = billboardMapper.LastBillboardSetData(request);
        System.out.println(responses);

        PageInfo<Billboard> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Billboard>>() {
        }.build(), new TypeBuilder<PageResult<Billboard>>() {}.build());
    }

    @Override
    public int getPublishNum(Long userId, Integer type) {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Billboard::getCreateBy, userId)
                .eq(Billboard::getType, type);
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        if(CollectionUtils.isNotEmpty(billboards)){
            return billboards.size();
        }

        return 0;
    }

    @Override
    public PageResult<MyPublishBillboardInfo> queryMyPublish(MyPublishBillboardRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<MyPublishBillboardInfo> list = billboardMapper.queryMyPublish(request.getUserId(), request.getType());
        if(CollectionUtils.isNotEmpty(list)){
            PageInfo<MyPublishBillboardInfo> pageInfo = new PageInfo<>(list);

            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<MyPublishBillboardInfo>>() {
            }.build(), new TypeBuilder<PageResult<MyPublishBillboardInfo>>() {}.build());
        }

        return new PageResult<MyPublishBillboardInfo>();
    }

    @Override
    public void updateMyBillboard(BillboardDTO billboardDTO) {
        Billboard billboard = billboardMapper.selectById(billboardDTO.getId());
        // BillboardDTO有null就不需要替换billboard
        BeanUtils.copyProperties(billboardDTO, billboard, CopyPropertionIngoreNull.getNullPropertyNames(billboard));

        billboardMapper.updateById(billboard);
    }

    @Override
    public int getMyReceiveBillboard(Long userId, Integer trueType) {
        int num = billboardMapper.getMyReceiveBillboard(userId, trueType);
        return num;
    }

    @Override
    public PageResult<MyReceiveBillboardInfo> queryMyReceiveBillboard(MyReceiveBillboardRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<MyReceiveBillboardInfo> responses = billboardMapper.queryMyReceiveBillboard(request);
        if(CollectionUtils.isNotEmpty(responses)){
            PageInfo<MyReceiveBillboardInfo> pageInfo = new PageInfo<>(responses);

            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<MyReceiveBillboardInfo>>() {
            }.build(), new TypeBuilder<PageResult<MyReceiveBillboardInfo>>() {}.build());
        }

        return new PageResult<MyReceiveBillboardInfo>();
    }

    @Override
    public IndexResponse getIndex() {
        IndexResponse indexResponse = new IndexResponse();

        // 最新的四条记录
        List<Billboard> billboards = getLast();
        List<BillboardIndexDTO> lasts = mapperFacade.mapAsList(billboards, BillboardIndexDTO.class);
        indexResponse.setGovs(lasts);
        // 政府榜
        List<Billboard> govs = getGovs();
        List<BillboardIndexDTO> govList = mapperFacade.mapAsList(govs, BillboardIndexDTO.class);
        indexResponse.setGovs(govList);

        // 企业榜
        List<Billboard> bizs = getBizs();
        List<BillboardIndexDTO> bizsList = mapperFacade.mapAsList(bizs, BillboardIndexDTO.class);
        indexResponse.setBizs(bizsList);

        return indexResponse;
    }

    @Override
    public PageResult<BillboardIndexDTO> queryGovBillborads(SearchGovRequest searchGovRequest) {
        PageHelper.startPage(searchGovRequest.getPageNum(), searchGovRequest.getPageSize());

        List<BillboardIndexDTO> billboards = billboardMapper.queryGovBillborads(searchGovRequest);
        if(CollectionUtils.isNotEmpty(billboards)){
            PageInfo<BillboardIndexDTO> pageInfo = new PageInfo<>(billboards);

            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<BillboardIndexDTO>>() {
            }.build(), new TypeBuilder<PageResult<BillboardIndexDTO>>() {}.build());
        }

        return null;
    }

    @Override
    public PageResult<BillboardIndexDTO> queryBizBillborads(SearchBizRequest searchGovRequest) {
        PageHelper.startPage(searchGovRequest.getPageNum(), searchGovRequest.getPageSize());

        List<BillboardIndexDTO> billboards = billboardMapper.queryBizBillborads(searchGovRequest);
        if(CollectionUtils.isNotEmpty(billboards)){
            PageInfo<BillboardIndexDTO> pageInfo = new PageInfo<>(billboards);

            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<BillboardIndexDTO>>() {
            }.build(), new TypeBuilder<PageResult<BillboardIndexDTO>>() {}.build());
        }

        return null;
    }

    private List<Billboard> getBizs() {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode());
        lambdaQueryWrapper.eq(Billboard::getType, BillboardTypeEnum.BUS_BILLBOARD.getCode());
        lambdaQueryWrapper.orderByDesc(Billboard::getIsTop, Billboard::getCreateAt);
        // 轮播3条， 普通6条
        lambdaQueryWrapper.last("LIMIT 5");
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        return billboards;
    }

    private List<Billboard> getGovs() {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode());
        lambdaQueryWrapper.eq(Billboard::getType, BillboardTypeEnum.GOV_BILLBOARD.getCode());
        lambdaQueryWrapper.orderByDesc(Billboard::getIsTop, Billboard::getCreateAt);
        // 轮播3条， 普通6条
        lambdaQueryWrapper.last("LIMIT 9");
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        return billboards;
    }

    private List<Billboard> getLast() {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode());
        lambdaQueryWrapper.orderByDesc(Billboard::getIsTop, Billboard::getCreateAt);
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        return billboards;
    }









}
