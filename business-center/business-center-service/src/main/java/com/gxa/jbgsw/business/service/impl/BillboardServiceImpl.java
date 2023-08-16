package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardGain;
import com.gxa.jbgsw.business.entity.Collection;
import com.gxa.jbgsw.business.entity.TalentPool;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.mapper.BillboardMapper;
import com.gxa.jbgsw.business.mapper.TalentPoolMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardHarvestRelatedService;
import com.gxa.jbgsw.business.service.BillboardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.CollectionService;
import com.gxa.jbgsw.business.service.TalentPoolService;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageRequest;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
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

@Slf4j
@Service
public class BillboardServiceImpl extends ServiceImpl<BillboardMapper, Billboard> implements BillboardService {
    @Resource
    BillboardMapper billboardMapper;
    @Resource
    TalentPoolMapper talentPoolMapper;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    BillboardHarvestRelatedService billboardHarvestRelatedService;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    CollectionService collectionService;
    @Resource
    TalentPoolService talentPoolService;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        billboardMapper.deleteBatchIds(list);

        // 收藏表也同时删除
        collectionService.deleteBatchByPid(list);
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

        List<MyPublishBillboardInfo> list = billboardMapper.queryMyPublish(request.getUserId(), request.getType(), request.getAuditStatus());
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

        // 组装keys
        StringBuffer sb = new StringBuffer();
        // 标题
        sb.append(billboardDTO.getTitle());
        sb.append(CharUtil.COMMA);
        // 技术关键字（直接输入）
        sb.append(billboardDTO.getTechKeys());
        sb.append(CharUtil.COMMA);
        // 工信大类
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(billboardDTO.getCategories()));
        if(dictionaryDTO != null){
            sb.append(dictionaryDTO.getDicValue());
        }
        sb.append(CharUtil.COMMA);
        sb.append(billboard.getProvinceName()).append(CharUtil.COMMA)
                .append(billboard.getCityName()).append(CharUtil.COMMA)
                .append(billboard.getAreaName());
        billboard.setQueryKeys(sb.toString());

        billboardMapper.updateById(billboard);
    }

    @Override
    public long getMyReceiveBillboard(Long userId, Integer trueType) {
        long num = billboardMapper.getMyReceiveBillboard(userId, trueType);
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
        if(CollectionUtils.isNotEmpty(lasts)){
            lasts.stream().forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
            });
        }
        indexResponse.setLasts(lasts);
        // 政府榜
        List<Billboard> govs = getGovs();
        List<BillboardIndexDTO> govList = mapperFacade.mapAsList(govs, BillboardIndexDTO.class);
        if(CollectionUtils.isNotEmpty(govList)){
            govList.stream().forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
            });
        }
        indexResponse.setGovs(govList);

        // 企业榜
        List<Billboard> bizs = getBizs();
        List<BillboardIndexDTO> bizsList = mapperFacade.mapAsList(bizs, BillboardIndexDTO.class);
        if(CollectionUtils.isNotEmpty(bizsList)){
            bizsList.stream().forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
            });
        }
        indexResponse.setBizs(bizsList);

        return indexResponse;
    }

    @Override
    public PageResult<BillboardIndexDTO> queryGovBillborads(SearchGovRequest searchGovRequest) {
        StopWatch stopWatch = new StopWatch();

        PageResult<BillboardIndexDTO> pageResult = new PageResult<BillboardIndexDTO>();

        PageHelper.startPage(searchGovRequest.getPageNum(), searchGovRequest.getPageSize());


        log.error("##################查询开始##################");
        stopWatch.start();
        List<BillboardIndexDTO> billboards = billboardMapper.queryGovBillborads(searchGovRequest);
        stopWatch.stop();
        log.error("##################查询结束##################");
        log.error("花费的时间：{}", stopWatch.getTotalTimeMillis());


        if(CollectionUtils.isNotEmpty(billboards)){
            PageInfo<BillboardIndexDTO> pageInfo = new PageInfo<>(billboards);

            pageResult.setList(billboards);
            pageResult.setPageNum(searchGovRequest.getPageNum());
            pageResult.setPages(pageInfo.getPages());
            pageResult.setPageSize(pageInfo.getPageSize());
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setSize(pageInfo.getSize());
        }

        return pageResult;
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

    @Override
    public List<RelateHavestDTO> getRelatedHavestByBillboardId(Long id, int i) {
        List<RelateHavestDTO> relateHavests = new ArrayList<>();

        List<BillboardHarvestRelatedResponse> relatedResponses = billboardHarvestRelatedService.getHarvestRecommend(id);
        if(relatedResponses != null && relatedResponses.size() >3){
            List<BillboardHarvestRelatedResponse> n1 = relatedResponses.subList(0, 2);
            relateHavests = mapperFacade.mapAsList(n1, RelateHavestDTO.class);
        }else if(relatedResponses != null && relatedResponses.size() <3){
            relateHavests = mapperFacade.mapAsList(relatedResponses, RelateHavestDTO.class);
        }
        return relateHavests;
    }

    @Override
    public List<Billboard> getRelateBillboardByCategories(Integer categories, Integer type) {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(type)){
            lambdaQueryWrapper.eq(Billboard::getType, BillboardTypeEnum.GOV_BILLBOARD.getCode());
        }else if(BillboardTypeEnum.BUS_BILLBOARD.getCode().equals(type)){
            lambdaQueryWrapper.eq(Billboard::getType, BillboardTypeEnum.BUS_BILLBOARD.getCode());
        }
        lambdaQueryWrapper.eq(Billboard::getCategories, categories);
        lambdaQueryWrapper.orderByDesc(Billboard::getCreateAt);
        lambdaQueryWrapper.last("LIMIT 5");
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        return billboards;
    }

    private List<Billboard> getBizs() {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 待审核
        lambdaQueryWrapper.eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode());
        // 已审核
        lambdaQueryWrapper.eq(Billboard::getAuditStatus, AuditingStatusEnum.PASS.getCode());
        // 企业吧榜
        lambdaQueryWrapper.eq(Billboard::getType, BillboardTypeEnum.BUS_BILLBOARD.getCode());
        lambdaQueryWrapper.orderByDesc(Billboard::getIsTop, Billboard::getCreateAt);
        // 轮播3条， 普通6条
        lambdaQueryWrapper.last("LIMIT 5");
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        return billboards;
    }

    private List<Billboard> getGovs() {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 待揭榜
        lambdaQueryWrapper.eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode());
        // 已审核
        lambdaQueryWrapper.eq(Billboard::getAuditStatus, AuditingStatusEnum.PASS.getCode());
        // 政府榜
        lambdaQueryWrapper.eq(Billboard::getType, BillboardTypeEnum.GOV_BILLBOARD.getCode());
        lambdaQueryWrapper.orderByDesc(Billboard::getIsTop, Billboard::getCreateAt);
        // 轮播3条， 普通6条
        lambdaQueryWrapper.last("LIMIT 9");
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        return billboards;
    }

    private List<Billboard> getLast() {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 已审核，待揭榜
        lambdaQueryWrapper.eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode())
                          .eq(Billboard::getAuditStatus, AuditingStatusEnum.PASS.getCode())
                          .orderByDesc(Billboard::getIsTop)
                          .orderByDesc(Billboard::getCreateAt)
                          .last("LIMIT  4");
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        return billboards;
    }

    @Override
    public void batchInsert(List<BillboardDTO> batchList) {
        List<Billboard> billboards = mapperFacade.mapAsList(batchList, Billboard.class);
        this.saveBatch(billboards);
    }

    @Override
    public void updateAuditStatus(BillboardAuditDTO billboardAuditDTO) {
        LambdaUpdateWrapper<Billboard> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Billboard::getAuditStatus, billboardAuditDTO.getAuditStatus());
        if(billboardAuditDTO.getAuditStatus().equals(2)){
            lambdaUpdateWrapper.set(Billboard::getReason, billboardAuditDTO.getReason());
        }
        lambdaUpdateWrapper.set(Billboard::getAuditUserId, billboardAuditDTO.getAuditUserId());
        lambdaUpdateWrapper.set(Billboard::getAuditCreateAt, billboardAuditDTO.getAuditCreateAt());
        lambdaUpdateWrapper.eq(Billboard::getId, billboardAuditDTO.getId());

        billboardMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public List<BillboardResponse> searchNew(Integer num) {
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 已审核通过
        lambdaQueryWrapper.eq(Billboard::getAuditStatus, AuditingStatusEnum.PASS.getCode());
        // 待揭榜
        lambdaQueryWrapper.eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode());
        lambdaQueryWrapper.orderByDesc(Billboard::getCreateAt);

        int start = (num - 1) * 5;
        int end = num * 5;

        lambdaQueryWrapper.last("LIMIT  "+start+", "+end+" ");
        List<Billboard> billboards = billboardMapper.selectList(lambdaQueryWrapper);

        if(CollectionUtils.isNotEmpty(billboards)){
            return  mapperFacade.mapAsList(billboards, BillboardResponse.class);
        }
        return null;
    }

    @Override
    public List<RelateTalentDTO> getRelatedTalentByKeys(SearchParamsDTO searchParams) {
        List<RelateTalentDTO> relateTalents = new ArrayList<>();
        List<TalentPool> talentPools = new ArrayList<>();
        LambdaQueryWrapper<TalentPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(searchParams.getSearchFiled())){
            lambdaQueryWrapper.like(TalentPool::getQueryKeys, searchParams.getSearchFiled());
            lambdaQueryWrapper.orderByDesc(TalentPool::getCreateAt);
            lambdaQueryWrapper.last("LIMIT "+searchParams.getPageNum()+"");
            talentPools = talentPoolMapper.selectList(lambdaQueryWrapper);
        }

        if(talentPools.size() == 0){
            lambdaQueryWrapper.orderByDesc(TalentPool::getCreateAt);
            lambdaQueryWrapper.last("LIMIT "+searchParams.getPageNum()+"");
            talentPools = talentPoolMapper.selectList(lambdaQueryWrapper);
        }

        if(talentPools != null){
            int total = searchParams.getPageNum();
            if(talentPools.size()<total){
                total = talentPools.size();
            }
            for(int i=0; i<total; i++){
                RelateTalentDTO relateTalent  = mapperFacade.map(talentPools.get(i), RelateTalentDTO.class);
                relateTalents.add(relateTalent);
            }
        }
        return relateTalents;
    }


}
