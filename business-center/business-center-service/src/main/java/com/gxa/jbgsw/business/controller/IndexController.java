package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.IndexApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.entity.TalentPool;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.*;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "web首页管理")
public class IndexController implements IndexApi {
    @Resource
    BillboardService billboardService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    HarvestService harvestService;
    @Resource
    TalentPoolService talentPoolService;
    @Resource
    TechEconomicManService techEconomicManService;
    @Resource
    BillboardTalentRelatedService billboardTalentRelatedService;
    @Resource
    BillboardHarvestRelatedService billboardHarvestRelatedService;
    @Resource
    NewsService newsService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public IndexResponse getIndex() {
        return billboardService.getIndex();
    }

    @Override
    public PageResult<BillboardIndexDTO> queryGovBillborads(SearchGovRequest searchGovRequest) {
        PageResult<BillboardIndexDTO> pageResult = billboardService.queryGovBillborads(searchGovRequest);

        List<BillboardIndexDTO> billboards = pageResult.getList();
        if(CollectionUtils.isNotEmpty(billboards)){
            List<BillboardIndexDTO> responses = mapperFacade.mapAsList(billboards, BillboardIndexDTO.class);
            responses.forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    // 工信大类名称
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
                // 状态名称
                s.setStatusName(BillboardStatusEnum.getNameByIndex(s.getStatus()));
            });
            pageResult.setList(responses);
        }

        return pageResult;
    }

    @Override
    public PageResult<BillboardIndexDTO> queryBizBillborads(SearchBizRequest searchGovRequest) {
        PageResult<BillboardIndexDTO> pageResult = billboardService.queryBizBillborads(searchGovRequest);

        List<BillboardIndexDTO> billboards = pageResult.getList();
        if(CollectionUtils.isNotEmpty(billboards) && billboards.size()>0 ){
            List<BillboardIndexDTO> responses = mapperFacade.mapAsList(billboards, BillboardIndexDTO.class);
            responses.forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    // 工信大类名称
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
                // 状态名称
                s.setStatusName(BillboardStatusEnum.getNameByIndex(s.getStatus()));
            });
            pageResult.setList(responses);
        }

        return pageResult;
    }

    @Override
    public PageResult<SearchHavestResponse> queryHarvests(SearchHarvestsRequest searchHarvestsRequest) {
        PageResult<SearchHavestResponse> pageResult = harvestService.queryHarvests(searchHarvestsRequest);
        return pageResult;
    }

    @Override
    public PageResult<SearchTalentsResponse> queryTalents(SearchTalentsRequest searchTalentsRequest) {
        PageResult<SearchTalentsResponse> pageResult = talentPoolService.queryTalents(searchTalentsRequest);
        return pageResult;
    }

    @Override
    public PageResult<SearchEconomicMansResponse> queryEconomicMans(SearchEconomicMansRequest searchTalentsRequest) {
        PageResult<SearchEconomicMansResponse> pageResult = techEconomicManService.queryEconomicMans(searchTalentsRequest);
        return pageResult;
    }

    @Override
    public PageResult<SearchNewsResponse> queryNews(SearchNewsRequest searchNewsRequest) {
        PageResult<SearchNewsResponse> pageResult = newsService.queryNews(searchNewsRequest);
        return pageResult;
    }

    @Override
    public RelateDTO getRelatedByBillboardId(Long id) {
        RelateDTO relateDTO = new RelateDTO();

        // 获取三条相关成果
        List<RelateHavestDTO> relateHavests = billboardService.getRelatedHavestByBillboardId(id, 3);
        if(CollectionUtils.isNotEmpty(relateHavests)){
            relateDTO.setHavests(relateHavests);
        }

        // 相关帅才推荐
        List<BillboardTalentRelatedResponse> talents = billboardTalentRelatedService.getTalentRecommend(id);
        if(CollectionUtils.isNotEmpty(talents)){
            List<RelateTalentDTO> talentList = mapperFacade.mapAsList(talents, RelateTalentDTO.class);
            relateDTO.setTalents(talentList);
        }

        // 相关榜单推荐
        Billboard billboard = billboardService.getById(id);
        List<Billboard> billboards = billboardService.getRelateBillboardByCategories(billboard.getCategories(), billboard.getType());
        if(billboards != null){
            List<RelateBillboardDTO> billboardList = mapperFacade.mapAsList(billboards, RelateBillboardDTO.class);
            relateDTO.setBillboards(billboardList);
        }

       return relateDTO;
    }

    @Override
    public RelateDTO getRelatedByHarvestId(Long id) {
        RelateDTO relateDTO = new RelateDTO();

        Harvest harvest = harvestService.getById(id);

        // 获取相关成果
        List<Harvest> harvests = harvestService.getHarvesByTechDomain(harvest.getTechDomain());
        if(harvests != null){
            List<RelateHavestDTO> havestList = mapperFacade.mapAsList(harvests, RelateHavestDTO.class);
            relateDTO.setHavests(havestList);
        }

        // 相关帅才推荐
        List<BillboardTalentRelatedResponse> talents = billboardTalentRelatedService.getTalentRecommend(id);
        if(CollectionUtils.isNotEmpty(talents)){
            List<RelateTalentDTO> talentList = mapperFacade.mapAsList(talents, RelateTalentDTO.class);
            relateDTO.setTalents(talentList);
        }

        // 相关榜单推荐
        List<BillboardResponse> billboardList = billboardHarvestRelatedService.getHarvestByHarvestId(id);
        if(billboardList != null){
            List<RelateBillboardDTO> blist = mapperFacade.mapAsList(billboardList, RelateBillboardDTO.class);
            relateDTO.setBillboards(blist);
        }

        return relateDTO;
    }

    @Override
    public RelateDTO getRelatedByTalentId(Long id) {
        RelateDTO relateDTO = new RelateDTO();

        TalentPool talentPool = talentPoolService.getById(id);

        // 获取相关成果
        List<Harvest> harvests = harvestService.getHarvesByTechDomain(talentPool.getTechDomain());
        if(harvests != null){
            List<RelateHavestDTO> havestList = mapperFacade.mapAsList(harvests, RelateHavestDTO.class);
            relateDTO.setHavests(havestList);
        }

        // 相关帅才推荐
        List<BillboardTalentRelatedResponse> talents = billboardTalentRelatedService.getTalentRecommend(id);
        if(CollectionUtils.isNotEmpty(talents)){
            List<RelateTalentDTO> talentList = mapperFacade.mapAsList(talents, RelateTalentDTO.class);
            relateDTO.setTalents(talentList);
        }

        // 相关榜单推荐
        List<BillboardResponse> billboardList = billboardHarvestRelatedService.getHarvestByHarvestId(id);
        if(billboardList != null){
            List<RelateBillboardDTO> blist = mapperFacade.mapAsList(billboardList, RelateBillboardDTO.class);
            relateDTO.setBillboards(blist);
        }

        return relateDTO;
    }


}
