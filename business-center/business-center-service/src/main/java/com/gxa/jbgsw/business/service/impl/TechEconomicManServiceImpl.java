package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.CharUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardEconomicRelated;
import com.gxa.jbgsw.business.entity.TechEconomicMan;
import com.gxa.jbgsw.business.mapper.TechEconomicManMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardEconomicRelatedStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.service.BillboardEconomicRelatedService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListResourceBundle;
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
    BillboardEconomicRelatedService billboardEconomicRelatedService;

    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        techEconomicManMapper.deleteBatchIds(list);

        // 删除 榜单与经纪人的匹配关联表 (根据经纪人ID删除该相关信息)
        billboardEconomicRelatedService.deleteByEconomicId(list);
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
                    List<HavestVO> havests = new ArrayList<>();

                    for(int i=0; i<bs.size(); i++){
                        BillboardHarvestRelatedResponse b = bs.get(i);
                        HavestVO vo = new HavestVO();
                        vo.setId(b.getId());
                        vo.setName(b.getName());

                        havests.add(vo);
                    }

                    s.setHavests(havests);
                }


                // 相关帅才
                List<BillboardTalentRelatedResponse> ts = billboardTalentRelatedService.getTalentRecommend(s.getBillboardId());
                if(CollectionUtils.isNotEmpty(ts)){
                    List<HavestVO> talents = new ArrayList<>();
                    for(int n=0; n<ts.size(); n++){
                        BillboardTalentRelatedResponse b = ts.get(n);
                        HavestVO vo = new HavestVO();
                        vo.setId(b.getId());
                        vo.setName(b.getName());

                        talents.add(vo);
                    }
                    s.setTalents(talents);
                }
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
            x.setEconomicId(request.getEconomicId());
            long buzs = getOrders(x);
            myOrderResponse.setBuzs(buzs);
            myOrderResponse.setGovs(pageInfo.getTotal());
        }else{
            // 查询政府榜的条数
            TechEconomicManRequiresRequest g = new TechEconomicManRequiresRequest();
            g.setType(BillboardTypeEnum.GOV_BILLBOARD.getCode());
            g.setEconomicId(request.getEconomicId());
            long govs = getOrders(g);
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
        responses.stream().forEach(s->{
            s.setStatusName(BillboardEconomicRelatedStatusEnum.getNameByIndex(s.getStatus()));

            // 相关成果
            List<BillboardHarvestRelatedResponse> bs = billboardHarvestRelatedService.getHarvestRecommend(s.getBillboardId());
            StringBuffer sb = new StringBuffer();
            if(CollectionUtils.isNotEmpty(bs)){
                List<HavestVO> havests = new ArrayList<>();

                for(int i=0; i<bs.size(); i++){
                    BillboardHarvestRelatedResponse b = bs.get(i);
                    HavestVO vo = new HavestVO();
                    vo.setId(b.getId());
                    vo.setName(b.getName());

                    havests.add(vo);
                }

                s.setHavests(havests);
            }

            // 相关帅才
            List<BillboardTalentRelatedResponse> ts = billboardTalentRelatedService.getTalentRecommend(s.getBillboardId());
            if(CollectionUtils.isNotEmpty(ts)){
                List<HavestVO> talents = new ArrayList<>();
                for(int n=0; n<ts.size(); n++){
                    BillboardTalentRelatedResponse b = ts.get(n);
                    HavestVO vo = new HavestVO();
                    vo.setId(b.getId());
                    vo.setName(b.getName());

                    talents.add(vo);
                }
                s.setTalents(talents);
            }
        });

        PageInfo<TechEconomicManRequiresResponse> pageInfo = new PageInfo<>(responses);
        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<TechEconomicManRequiresResponse>>() {
        }.build(), new TypeBuilder<PageResult<TechEconomicManRequiresResponse>>() {}.build());
    }

    @Override
    public TechEconomicManDTO getTechEconomicManByMobile(String mobile) {
        LambdaQueryWrapper<TechEconomicMan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TechEconomicMan::getMobile, mobile);
        List<TechEconomicMan> techEconomics = techEconomicManMapper.selectList(lambdaQueryWrapper);
        if(techEconomics != null && techEconomics.size()>0){
            TechEconomicMan techEconomicMan = techEconomics.get(0);

            return mapperFacade.map(techEconomicMan, TechEconomicManDTO.class);
        }

        return null;
    }

    @Override
    public void deleteAgreements(Long id) {
        LambdaUpdateWrapper<TechEconomicMan> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(TechEconomicMan::getAgreements, null)
                .set(TechEconomicMan::getAgreementsName, null)
                .eq(TechEconomicMan::getId, id);

        techEconomicManMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void pipei(Long id) {
        billboardEconomicRelatedService.addEconomicRelatedByEcomicId(id);
    }

    @Override
    public void insert(TechEconomicMan techEconomicMan) {
        techEconomicManMapper.insert(techEconomicMan);
    }

    private long getOrders(TechEconomicManRequiresRequest request) {
        long num = 0;
        num = techEconomicManMapper.getOrders(request);

        return num;
    }





}
