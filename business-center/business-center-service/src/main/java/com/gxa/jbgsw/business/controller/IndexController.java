package com.gxa.jbgsw.business.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.IndexApi;
import com.gxa.jbgsw.business.entity.*;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.*;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.PageRequest;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    CompanyService companyService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public IndexResponse getIndex() {
        return billboardService.getIndex();
    }

    @Override
    public PageResult<BillboardIndexDTO> queryGovBillborads(SearchGovRequest searchGovRequest) {
        PageResult<BillboardIndexDTO> pageResult = billboardService.queryGovBillborads(searchGovRequest);

        if(pageResult != null){
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
        }

        return pageResult;
    }

    @Override
    public PageResult<BillboardIndexDTO> queryBizBillborads(SearchBizRequest searchGovRequest) {
        PageResult<BillboardIndexDTO> pageResult = billboardService.queryBizBillborads(searchGovRequest);

        if(pageResult != null){
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
        }else{
            // 如果没有数据，就默认的取
            Billboard billboard = billboardService.getById(id);
            List<Harvest>  harvests = harvestService.getHarvesByTechDomain(billboard.getTechKeys());
            if(CollectionUtils.isNotEmpty(harvests)){
                if(harvests.size()>=3){
                    harvests = harvests.subList(0,2);
                    List<RelateHavestDTO> havestDTOList = mapperFacade.mapAsList(harvests, RelateHavestDTO.class);
                    relateDTO.setHavests(havestDTOList);
                }
            }

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

    @Override
    public PcIndexSearchResponse search(PcIndexSearchRequest pcIndexSearchRequest) {
        PcIndexSearchResponse pcIndexSearchResponse = new PcIndexSearchResponse();

        // 政府榜
        SearchGovRequest searchGovRequest = new SearchGovRequest();
        searchGovRequest.setPageNum(1);
        searchGovRequest.setPageSize(2);
        searchGovRequest.setType(BillboardTypeEnum.GOV_BILLBOARD.getCode());
        searchGovRequest.setSearchFiled(pcIndexSearchRequest.getSearchFiled());
        PageResult<BillboardIndexDTO> pageResult = billboardService.queryGovBillborads(searchGovRequest);
        pcIndexSearchResponse.setGovBillboradsNum(pageResult.getTotal());
        pcIndexSearchResponse.setGovBillborads(pageResult.getList());

        // 企业榜
        SearchGovRequest searchbizRequest = new SearchGovRequest();
        searchbizRequest.setPageNum(1);
        searchbizRequest.setPageSize(2);
        searchbizRequest.setType(BillboardTypeEnum.BUS_BILLBOARD.getCode());
        searchbizRequest.setSearchFiled(pcIndexSearchRequest.getSearchFiled());
        PageResult<BillboardIndexDTO> pageResultBiz = billboardService.queryGovBillborads(searchbizRequest);
        pcIndexSearchResponse.setBizBillboradsNum(pageResultBiz.getTotal());
        pcIndexSearchResponse.setBizBillborads(pageResultBiz.getList());

        // 成果
        SearchHarvestsRequest searchHarvestsRequest = new SearchHarvestsRequest();
        searchHarvestsRequest.setPageNum(1);
        searchHarvestsRequest.setPageSize(2);
        searchHarvestsRequest.setSearchFiled(pcIndexSearchRequest.getSearchFiled());
        PageResult<SearchHavestResponse> pageResultHarvests = harvestService.queryHarvests(searchHarvestsRequest);
        pcIndexSearchResponse.setHavestsNum(pageResultHarvests.getTotal());
        pcIndexSearchResponse.setHavests(pageResultHarvests.getList());

        // 帅才
        TalentPoolRequest talentPoolRequest = new TalentPoolRequest();
        talentPoolRequest.setPageNum(1);
        talentPoolRequest.setPageSize(2);
        talentPoolRequest.setSearchFiled(pcIndexSearchRequest.getSearchFiled());
        PageResult<TalentPoolResponse> pageResultTalents = talentPoolService.pageQuery(talentPoolRequest);
        pcIndexSearchResponse.setTalentsNum(pageResultTalents.getTotal());
        pcIndexSearchResponse.setTalents(pageResultTalents.getList());

        // 团队
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setPageNum(1);
        companyRequest.setPageSize(2);
        companyRequest.setSearchFiled(pcIndexSearchRequest.getSearchFiled());
        PageResult<Company> pageQuerCompansy = companyService.pageQuery(companyRequest);
        pcIndexSearchResponse.setTeamsNum(pageQuerCompansy.getTotal());
        if(pageQuerCompansy.getList() !=null){
            List<SearchTeamsResponse> searchTeamsResponses = mapperFacade.mapAsList(pageQuerCompansy.getList(), SearchTeamsResponse.class);
            pcIndexSearchResponse.setTeams(searchTeamsResponses);
        }

        // 政策
        SearchNewsRequest newsRequest = new SearchNewsRequest();
        newsRequest.setPageNum(1);
        newsRequest.setPageSize(2);
        newsRequest.setSearchFiled(pcIndexSearchRequest.getSearchFiled());
        PageResult<SearchNewsResponse> pageResultNews = newsService.queryNews(newsRequest);
        pcIndexSearchResponse.setPolicyNum(pageResultNews.getTotal());
        pcIndexSearchResponse.setPolicys(pageResultNews.getList());

        return pcIndexSearchResponse;
    }

    @Override
    public PageResult<SearchTeamsResponse> searchTeamRequest(CompanyRequest companyRequest) {
        PageHelper.startPage(companyRequest.getPageNum(), companyRequest.getPageSize());
        // 团队
        PageResult<Company> pageResult = companyService.pageQuery(companyRequest);
        if(pageResult.getList() !=null){
            List<SearchTeamsResponse> searchTeamsResponses = mapperFacade.mapAsList(pageResult.getList(), SearchTeamsResponse.class);
            PageInfo<SearchTeamsResponse> pageInfo = new PageInfo<>(searchTeamsResponses);

            //类型转换
            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<SearchTeamsResponse>>() {
            }.build(), new TypeBuilder<PageResult<SearchTeamsResponse>>() {}.build());

        }

        return null;
    }

    @Override
    public List<BillboardResponse> searchNew(Integer num) {
        return billboardService.searchNew(num);
    }


}
