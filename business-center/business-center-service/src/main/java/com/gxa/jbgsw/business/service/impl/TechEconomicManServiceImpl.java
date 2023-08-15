package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.CharUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardEconomicRelated;
import com.gxa.jbgsw.business.entity.TechEconomicMan;
import com.gxa.jbgsw.business.mapper.TechEconomicManMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardEconomicRelatedStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.service.BillboardHarvestRelatedService;
import com.gxa.jbgsw.business.service.BillboardTalentRelatedService;
import com.gxa.jbgsw.business.service.TechEconomicManService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 技术经济人 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class TechEconomicManServiceImpl extends ServiceImpl<TechEconomicManMapper, TechEconomicMan> implements TechEconomicManService {
    @Resource
    TechEconomicManMapper techEconomicManMapper;
    @Resource
    BillboardHarvestRelatedService billboardHarvestRelatedService;
    @Resource
    BillboardTalentRelatedService billboardTalentRelatedService;

    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        techEconomicManMapper.deleteBatchIds(list);
    }

    @Override
    public PageResult<TechEconomicMan> pageQuery(TechEconomicManRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<TechEconomicMan> responses = techEconomicManMapper.pageQuery(request);
        PageInfo<TechEconomicMan> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<TechEconomicMan>>() {
        }.build(), new TypeBuilder<PageResult<TechEconomicMan>>() {}.build());
    }

    @Override
    public PageResult<SearchEconomicMansResponse> queryEconomicMans(SearchEconomicMansRequest searchTalentsRequest) {
        PageHelper.startPage(searchTalentsRequest.getPageNum(), searchTalentsRequest.getPageSize());

        List<SearchEconomicMansResponse> responses = techEconomicManMapper.queryEconomicMans(searchTalentsRequest);
        PageInfo<SearchEconomicMansResponse> pageInfo = new PageInfo<>(responses);
        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<SearchEconomicMansResponse>>() {
        }.build(), new TypeBuilder<PageResult<SearchEconomicMansResponse>>() {}.build());
    }


    @Override
    public MyOrderResponse getEconomicManRequires(TechEconomicManRequiresRequest request) {
        MyOrderResponse myOrderResponse = new MyOrderResponse();

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<TechEconomicManRequiresResponse> responses = techEconomicManMapper.getEconomicManRequires(request);
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                s.setStatusName(BillboardEconomicRelatedStatusEnum.getNameByIndex(s.getStatus()));

                // 相关成果
                List<BillboardHarvestRelatedResponse> bs = billboardHarvestRelatedService.getHarvestRecommend(s.getBillboardId());
                StringBuffer sb = new StringBuffer();
                if(CollectionUtils.isNotEmpty(bs)){
                    for(int i=0; i<bs.size(); i++){
                        if(i == bs.size() - 1){
                            sb.append(bs.get(i).getName());
                        }else{
                            sb.append(bs.get(i).getName()).append(CharUtil.COMMA);
                        }
                    }
                }
                s.setHavests(sb.toString());

                // 相关帅才
                StringBuffer tbs = new StringBuffer();
                List<BillboardTalentRelatedResponse> ts = billboardTalentRelatedService.getTalentRecommend(s.getBillboardId());
                if(CollectionUtils.isNotEmpty(ts)){
                    for(int i=0; i<ts.size(); i++){
                        if(i == ts.size() - 1){
                            tbs.append(ts.get(i).getName());
                        }else{
                            tbs.append(ts.get(i).getName()).append(CharUtil.COMMA);
                        }
                    }
                }
                s.setTalents(tbs.toString());
            });
        }

        PageInfo<TechEconomicManRequiresResponse> pageInfo = new PageInfo<>(responses);
        myOrderResponse.setPageNum(pageInfo.getPageNum());
        myOrderResponse.setPages(pageInfo.getPages());
        myOrderResponse.setPageSize(pageInfo.getPageSize());
        myOrderResponse.setSize(pageInfo.getSize());
        myOrderResponse.setTotal(pageInfo.getTotal());
        myOrderResponse.setRequires(pageInfo.getList());

        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            // 查询企业榜的条数
            TechEconomicManRequiresRequest x = new TechEconomicManRequiresRequest();
            x.setType(BillboardTypeEnum.BUS_BILLBOARD.getCode());
            long buzs = getOrders(request);
            myOrderResponse.setBuzs(buzs);
            myOrderResponse.setGovs(pageInfo.getTotal());
        }else{
            // 查询政府榜的条数
            TechEconomicManRequiresRequest g = new TechEconomicManRequiresRequest();
            g.setType(BillboardTypeEnum.GOV_BILLBOARD.getCode());
            long govs = getOrders(request);
            myOrderResponse.setGovs(govs);
            myOrderResponse.setBuzs(pageInfo.getTotal());
        }

        return myOrderResponse;
    }

    @Override
    public List<String> getLabels() {
        return techEconomicManMapper.getLabels();
    }

    @Override
    public PageResult<TechEconomicManRequiresResponse> queryEconomicManRequires(TechEconomicManRequiresRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<TechEconomicManRequiresResponse> responses = techEconomicManMapper.getEconomicManRequires(request);
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                s.setStatusName(BillboardEconomicRelatedStatusEnum.getNameByIndex(s.getStatus()));

                // 相关成果
                List<BillboardHarvestRelatedResponse> bs = billboardHarvestRelatedService.getHarvestRecommend(s.getBillboardId());
                StringBuffer sb = new StringBuffer();
                if(CollectionUtils.isNotEmpty(bs)){
                    for(int i=0; i<bs.size(); i++){
                        if(i == bs.size() - 1){
                            sb.append(bs.get(i).getName());
                        }else{
                            sb.append(bs.get(i).getName()).append(CharUtil.COMMA);
                        }
                    }
                }
                s.setHavests(sb.toString());

                // 相关帅才
                StringBuffer tbs = new StringBuffer();
                List<BillboardTalentRelatedResponse> ts = billboardTalentRelatedService.getTalentRecommend(s.getBillboardId());
                if(CollectionUtils.isNotEmpty(ts)){
                    for(int i=0; i<ts.size(); i++){
                        if(i == ts.size() - 1){
                            tbs.append(ts.get(i).getName());
                        }else{
                            tbs.append(ts.get(i).getName()).append(CharUtil.COMMA);
                        }
                    }
                }
                s.setTalents(tbs.toString());
            });
        }

        PageInfo<TechEconomicManRequiresResponse> pageInfo = new PageInfo<>(responses);
        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<TechEconomicManRequiresResponse>>() {
        }.build(), new TypeBuilder<PageResult<TechEconomicManRequiresResponse>>() {}.build());
    }

    private long getOrders(TechEconomicManRequiresRequest request) {
        long num = 0;

        num = techEconomicManMapper.getOrders(request);

        return num;
    }





}
