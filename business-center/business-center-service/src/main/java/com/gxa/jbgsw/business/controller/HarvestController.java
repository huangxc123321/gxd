package com.gxa.jbgsw.business.controller;


import cn.hutool.core.util.CharUtil;
import com.gxa.jbgsw.basis.client.TechnicalFieldClassifyApi;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.client.HarvestApi;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.business.service.PatentService;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "成果管理")
public class HarvestController implements HarvestApi {
    @Resource
    HarvestService harvestService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;
    @Resource
    PatentService patentService;
    @Resource
    MapperFacade mapperFacade;


    @Override
    public PageResult<HarvestResponse> pageQuery(HarvestRequest request) {
        PageResult<HarvestResponse> pages = new PageResult<>();

        PageResult<HarvestResponse> pageResult = harvestService.pageQuery(request);
        List<HarvestResponse> harvests = pageResult.getList();
        if(CollectionUtils.isNotEmpty(harvests)){
            harvests.forEach(s->{
                // 技术领域
                StringBuffer sb = new StringBuffer();
                TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain()));
                if(tfc1 != null){
                    sb.append(tfc1.getName());
                    sb.append(CharUtil.COMMA);
                    TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc1.getPid()));
                    if(tfc2 != null){
                        sb.append(tfc2.getName());
                        sb.append(CharUtil.COMMA);
                        TechnicalFieldClassifyDTO tfc3 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc2.getPid()));
                        if(tfc3 != null){
                            sb.append(tfc3.getName());
                        }
                    }
                }
                s.setTechDomainName(sb.toString().replace("所有分类,", ""));

            });
            pages.setList(harvests);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }

    @Override
    public void add(HavestDTO havestDTO) {
        Harvest harvest = mapperFacade.map(havestDTO, Harvest.class);
        harvest.setCreateAt(new Date());

        // trade_type + tech_domain + maturity_level + name
        // 组装keys
        StringBuffer sb = new StringBuffer();
        // 标题
        sb.append(havestDTO.getName());
        sb.append(CharUtil.COMMA);
        // 成熟度
        DictionaryDTO dic = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.maturity_level.name(), String.valueOf(havestDTO.getMaturityLevel()));
        if(dic != null){
            sb.append(dic.getDicValue());
        }
        sb.append(CharUtil.COMMA);
        // 行业类型
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.trade_type.name(), String.valueOf(havestDTO.getTradeType()));
        if(dictionaryDTO != null){
            sb.append(dictionaryDTO.getDicValue());
        }
        // 技术领域
        TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(havestDTO.getTechDomain()));
        if(tfc1 != null){
            sb.append(tfc1.getName());
            sb.append(CharUtil.COMMA);
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc1.getPid()));
            if(tfc2 != null){
                sb.append(tfc2.getName());
                sb.append(CharUtil.COMMA);
                TechnicalFieldClassifyDTO tfc3 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc2.getPid()));
                if(tfc3 != null){
                    sb.append(tfc3.getName());
                }
            }
        }
        harvest.setQueryKeys(sb.toString());
        harvestService.saveHarvest(harvest, havestDTO.getPatents());
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        harvestService.deleteBatchIds(ids);
    }

    @Override
    public void update(HavestDTO havestDTO) {
        Harvest harvest = harvestService.getById(havestDTO.getId());

        // havestDTO有null就不需要替换harvest
        BeanUtils.copyProperties(havestDTO, harvest, CopyPropertionIngoreNull.getNullPropertyNames(harvest));
        harvestService.updateById(harvest);
    }

    @Override
    public List<HavestDTO> getHarvesByHolder(String holder) {
        List<Harvest> harvests = harvestService.getHarvesByHolder(holder);
        if(harvests == null){
            return new ArrayList<>();
        }

        return mapperFacade.mapAsList(harvests, HavestDTO.class);
    }

    @Override
    public HavestDetailInfo detail(Long id) {
        Harvest harvest = harvestService.getById(id);

        HavestDetailInfo havestDetailInfo = mapperFacade.map(harvest, HavestDetailInfo.class);
        // 技术领域
        StringBuffer sb = new StringBuffer();
        TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(harvest.getTechDomain()));
        if(tfc1 != null){
            sb.append(tfc1.getName());
            sb.append(CharUtil.COMMA);
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc1.getPid()));
            if(tfc2 != null){
                sb.append(tfc2.getName());
                sb.append(CharUtil.COMMA);
                TechnicalFieldClassifyDTO tfc3 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc2.getPid()));
                if(tfc3 != null){
                    sb.append(tfc3.getName());
                }
            }
        }
        havestDetailInfo.setTechKeys(sb.toString().replace("所有分类,", ""));
        havestDetailInfo.setTechDomainName(sb.toString().replace("所有分类,", ""));
        // 成熟度
        DictionaryDTO dicMaturityLevel = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.maturity_level.name(), String.valueOf(harvest.getMaturityLevel()));
        if(dicMaturityLevel != null){
            havestDetailInfo.setMaturityLevelName(dicMaturityLevel.getDicValue());
        }

        return havestDetailInfo;
    }

    @Override
    public HavestDTO getHavestById(Long id) {
        Harvest harvest = harvestService.getById(id);
        HavestDTO havestDTO = mapperFacade.map(harvest, HavestDTO.class);

        if(harvest.getIsPatent() != null && harvest.getIsPatent().equals(Integer.valueOf(1))){
            List<PatentDTO> patents = patentService.getPatentByPid(id);
            havestDTO.setPatents(patents);
        }
        return havestDTO;
    }

    @Override
    public List<RecommendHavestResponse> getRecommendHavest() {
        return harvestService.getRecommendHavest();
    }

    @Override
    public PageResult<HarvestResponse> pageMyHarvestQuery(HarvestRequest request) {
        PageResult<HarvestResponse> pages = new PageResult<>();

        PageResult<Harvest> pageResult = harvestService.pageMyHarvestQuery(request);
        List<Harvest> harvests = pageResult.getList();
        if(CollectionUtils.isNotEmpty(harvests)){
            List<HarvestResponse> responses = mapperFacade.mapAsList(harvests, HarvestResponse.class);
            responses.forEach(s->{

                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(""));
                if(dictionaryDTO != null){


                }
            });
            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }

    @Override
    public List<String> getContacts(String contacts) {
        return harvestService.getContacts(contacts);
    }

}

