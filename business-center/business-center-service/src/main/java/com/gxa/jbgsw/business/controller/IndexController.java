package com.gxa.jbgsw.business.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.client.IndexApi;
import com.gxa.jbgsw.business.entity.*;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.protocol.enums.LevelEnum;
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
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;
    @Resource
    NewsService newsService;
    @Resource
    CompanyService companyService;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    HotSearchWordService hotSearchWordService;

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

        // 生成热搜
        if(StrUtil.isNotBlank(searchGovRequest.getSearchFiled())){
            hotSearchWordService.add(searchGovRequest.getSearchFiled());
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
        }else{
            LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.orderByDesc(Billboard::getCreateAt)
                    .last("limit 5");
            billboards = billboardService.list(lambdaQueryWrapper);
            List<RelateBillboardDTO> blist = mapperFacade.mapAsList(billboards, RelateBillboardDTO.class);
            relateDTO.setBillboards(blist);
        }

       return relateDTO;
    }

    @Override
    public RelateDTO getRelatedByHarvestId(Long id) {
        RelateDTO relateDTO = new RelateDTO();

        Harvest harvest = harvestService.getById(id);

        // 获取相关成果 (三条)
        String key = "";
        if(harvest.getTechDomain() != null){
            Long techDomainId = Long.valueOf(harvest.getTechDomain());
            TechnicalFieldClassifyDTO technicalFieldClassifyDTO = technicalFieldClassifyFeignApi.getById(techDomainId);
            if(technicalFieldClassifyDTO != null){
                key = technicalFieldClassifyDTO.getName();
            }
        }
        List<Harvest> harvests = harvestService.getHarvesByTechDomainLimit(key, 3);
        if(harvests == null){
            LambdaUpdateWrapper<Harvest> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.orderByDesc(Harvest::getCreateAt)
                               .last(" LIMIT 3 ");
            harvests = harvestService.list(lambdaUpdateWrapper);
        }
        List<RelateHavestDTO> havestList = mapperFacade.mapAsList(harvests, RelateHavestDTO.class);
        if(havestList != null && havestList.size() >0){
            havestList.stream().forEach(s->{
                StringBuffer sb = new StringBuffer();

                if(s.getTechDomain1() != null && !"".equals(s.getTechDomain1())){
                    TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain1()));
                    if(tfc1 != null){
                        sb.append(tfc1.getName()).append(";");
                    }
                }
                if(s.getTechDomain2() != null  && !"".equals(s.getTechDomain2())){
                    TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain2()));
                    if(tfc2 != null){
                        sb.append(tfc2.getName()).append(";");
                    }
                }
                if(s.getTechDomain() != null  && !"".equals(s.getTechDomain())){
                    TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain()));
                    if(tfc != null){
                        s.setTechDomainName(tfc.getName());
                        sb.append(tfc.getName());
                    }
                }
                s.setTechDomainName(sb.toString());
            });
        }

        relateDTO.setHavests(havestList);

        // 相关帅才推荐（一条）
        List<BillboardTalentRelatedResponse> talents = billboardTalentRelatedService.getTalentRecommend(id);
        if(CollectionUtils.isNotEmpty(talents)){
            List<RelateTalentDTO> talentList = mapperFacade.mapAsList(talents, RelateTalentDTO.class);
            relateDTO.setTalents(talentList.subList(0,1));
        }else{
            LambdaUpdateWrapper<TalentPool> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.orderByDesc(TalentPool::getCreateAt)
                    .last(" LIMIT 1 ");
            List<TalentPool> talentPools = talentPoolService.list(lambdaUpdateWrapper);
            List<RelateTalentDTO> talentList = mapperFacade.mapAsList(talentPools, RelateTalentDTO.class);
            talentList.stream().forEach(s->{
                DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                if(dicProfessional != null){
                    s.setProfessionalName(dicProfessional.getDicValue());
                }
            });
            relateDTO.setTalents(talentList);
        }

        // 相关榜单推荐（五条）
        List<BillboardResponse> billboardList = billboardHarvestRelatedService.getHarvestByHarvestId(id);
        if(CollectionUtils.isNotEmpty(billboardList)){
            List<RelateBillboardDTO> blist = mapperFacade.mapAsList(billboardList, RelateBillboardDTO.class);
            if(blist.size()>=5){
                relateDTO.setBillboards(blist.subList(0,5));
            }else{
                relateDTO.setBillboards(blist);
            }
        }else{
            LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.orderByDesc(Billboard::getCreateAt)
                              .last("LIMIT 5");
            List<Billboard> billboards = billboardService.list(lambdaQueryWrapper);
            List<RelateBillboardDTO> blist = mapperFacade.mapAsList(billboards, RelateBillboardDTO.class);
            relateDTO.setBillboards(blist);
        }

        return relateDTO;
    }

    @Override
    public RelateDTO getRelatedByTalentId(Long id) {
        RelateDTO relateDTO = new RelateDTO();

        TalentPool talentPool = talentPoolService.getById(id);

        // 获取相关成果
        // 获取相关成果 (三条)
        String key = "";
        if(talentPool.getTechDomain2() != null){
            Long techDomainId = Long.valueOf(talentPool.getTechDomain2());
            TechnicalFieldClassifyDTO technicalFieldClassifyDTO = technicalFieldClassifyFeignApi.getById(techDomainId);
            if(technicalFieldClassifyDTO != null){
                key = technicalFieldClassifyDTO.getName();
            }
        }else{
            Long techDomainId = Long.valueOf(talentPool.getTechDomain1());
            TechnicalFieldClassifyDTO technicalFieldClassifyDTO = technicalFieldClassifyFeignApi.getById(techDomainId);
            if(technicalFieldClassifyDTO != null){
                key = technicalFieldClassifyDTO.getName();
            }
        }

        if(talentPool.getTechDomain() != null){
            Long techDomainId = Long.valueOf(talentPool.getTechDomain());
            TechnicalFieldClassifyDTO technicalFieldClassifyDTO = technicalFieldClassifyFeignApi.getById(techDomainId);
            if(technicalFieldClassifyDTO != null){
                key = technicalFieldClassifyDTO.getName();
            }
        }

        List<Harvest> harvests = harvestService.getHarvesByTechDomainLimit(key, 3);
        if(harvests == null){
            LambdaUpdateWrapper<Harvest> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.orderByDesc(Harvest::getCreateAt)
                    .last(" LIMIT 3 ");
            harvests = harvestService.list(lambdaUpdateWrapper);
        }
        List<RelateHavestDTO> havestList = mapperFacade.mapAsList(harvests, RelateHavestDTO.class);
        if(CollectionUtils.isNotEmpty(havestList)){
            havestList.stream().forEach(s->{
                StringBuffer sb = new StringBuffer();
                if(s.getTechDomain1() != null && !"".equals(s.getTechDomain1())){
                    TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain1()));
                    if(tfc1 != null){
                        sb.append(tfc1.getName()).append(";");
                    }
                }
                if(s.getTechDomain2() != null  && !"".equals(s.getTechDomain2())){
                    TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain2()));
                    if(tfc2 != null){
                        sb.append(tfc2.getName()).append(";");
                    }
                }
                if(s.getTechDomain() != null  && !"".equals(s.getTechDomain())){
                    TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain()));
                    if(tfc != null){
                        sb.append(tfc.getName());
                    }
                }
                s.setTechDomainName(sb.toString());
            });
        }

        relateDTO.setHavests(havestList);

        // 相关帅才推荐（一条）
        List<BillboardTalentRelatedResponse> talents = billboardTalentRelatedService.getTalentRecommend(id);
        if(CollectionUtils.isNotEmpty(talents)){
            List<RelateTalentDTO> talentList = mapperFacade.mapAsList(talents, RelateTalentDTO.class);
            relateDTO.setTalents(talentList.subList(0,1));
        }else{
            LambdaUpdateWrapper<TalentPool> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.orderByDesc(TalentPool::getCreateAt)
                    .last(" LIMIT 1 ");
            List<TalentPool> talentPools = talentPoolService.list(lambdaUpdateWrapper);
            List<RelateTalentDTO> talentList = mapperFacade.mapAsList(talentPools, RelateTalentDTO.class);
            talentList.stream().forEach(s->{
                DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                if(dicProfessional != null){
                    s.setProfessionalName(dicProfessional.getDicValue());
                }
            });
            relateDTO.setTalents(talentList);
        }

        // 相关榜单推荐（五条）
        List<BillboardResponse> billboardList = billboardHarvestRelatedService.getHarvestByHarvestId(id);
        if(CollectionUtils.isNotEmpty(billboardList)){
            List<RelateBillboardDTO> blist = mapperFacade.mapAsList(billboardList, RelateBillboardDTO.class);
            if(blist.size()>=5){
                relateDTO.setBillboards(blist.subList(0,5));
            }else{
                relateDTO.setBillboards(blist);
            }
        }else{
            LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.orderByDesc(Billboard::getCreateAt)
                    .last("LIMIT 5");
            List<Billboard> billboards = billboardService.list(lambdaQueryWrapper);
            List<RelateBillboardDTO> blist = mapperFacade.mapAsList(billboards, RelateBillboardDTO.class);
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

        return new PageResult<SearchTeamsResponse>();
    }

    @Override
    public List<BillboardResponse> searchNew(Integer num) {
        return billboardService.searchNew(num);
    }

    @Override
    public List<RelateTalentDTO> getRelatedTalentByKeys(SearchParamsDTO searchParams) {
        return billboardService.getRelatedTalentByKeys(searchParams);
    }

    @Override
    public List<RelateHavestDTO> getRelatedHavestByBillboardId(Long id) {
        return billboardHarvestRelatedService.getRelatedHavestByBillboardId(id);
    }

    @Override
    public List<RelateTalentDTO> getRelatedTalentByBillboardId(Long id) {
        return billboardTalentRelatedService.getRelatedTalentByBillboardId(id);
    }

    @Override
    public List<RelateBillboardDTO> getRelatedBillboardByBillboardId(Long id) {
        return billboardService.getRelatedBillboardByBillboardId(id);
    }

    @Override
    public List<RelateHavestDTO> getRelatedHavestByCompanyId(Long id) {
        List<Harvest> harvests = harvestService.getRelatedHavestByCompanyId(id);

        List<RelateHavestDTO> relateHavests = mapperFacade.mapAsList(harvests, RelateHavestDTO.class);
        return relateHavests;
    }

    @Override
    public List<RelateTalentDTO> getRelatedTalentByCompanyId(Long id) {
        List<TalentPool> talentPools = talentPoolService.getRelatedTalentByCompanyId(id);
        if(CollectionUtils.isNotEmpty(talentPools)){
            List<RelateTalentDTO> relateTalents = mapperFacade.mapAsList(talentPools, RelateTalentDTO.class);
            relateTalents.stream().forEach(s->{
                // 职称
                DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                if(dicProfessional != null){
                    s.setProfessionalName(dicProfessional.getDicValue());
                }
            });
        }

        return new ArrayList<>();
    }

    @Override
    public List<RelateBillboardDTO> getRelatedBillboardByCompanyId(Long id) {
        List<Billboard> billboards = billboardService.getRelatedBillboardByCompanyId(id);

        if(CollectionUtils.isNotEmpty(billboards)){
            List<RelateBillboardDTO> relateBillboards = mapperFacade.mapAsList(billboards, RelateBillboardDTO.class);
            return relateBillboards;
        }

        return  new ArrayList<>();

    }

    @Override
    public RelateEconomicDTO getRelatedByEconomicId(Long id) {
        RelateEconomicDTO relateDTO = new RelateEconomicDTO();
        TechEconomicMan techEconomicMan = techEconomicManService.getById(id);
        if(techEconomicMan != null){
            // 三个成果推荐
            SearchHarvestsRequest request = new SearchHarvestsRequest();
            request.setPageNum(1);
            request.setPageSize(3);
/*            String[] labels = techEconomicMan.getLabel().split("；");
            if(labels.length == 1){
                labels = techEconomicMan.getLabel().split(";");
            }
            String label = labels[0];
            request.setSearchFiled(label);*/

            List<SearchHavestResponse> totalSearchHavestResponse = new ArrayList<>();
            PageResult<SearchHavestResponse> pageResult = harvestService.queryHarvests(request);
/*            if(pageResult.getTotal() <3){
                if(pageResult.getList() != null && pageResult.getList().size() >0){
                    pageResult.getList().stream().forEach(s->{
                        totalSearchHavestResponse.add(s);
                    });
                }

               if(labels.length>=2 && totalSearchHavestResponse.size()<3){
                   label = labels[1];
                   request.setSearchFiled(label);
                   PageResult<SearchHavestResponse> pageResult1 = harvestService.queryHarvests(request);
                   if(pageResult1.getList() != null && pageResult1.getList().size() >0){
                       pageResult1.getList().stream().forEach(s->{
                           totalSearchHavestResponse.add(s);
                       });
                   }
               }

                if(labels.length>=3 && totalSearchHavestResponse.size()<3){
                    label = labels[2];
                    request.setSearchFiled(label);
                    PageResult<SearchHavestResponse> pageResult2 = harvestService.queryHarvests(request);
                    if(pageResult2.getList() != null && pageResult2.getList().size() >0){
                        pageResult2.getList().stream().forEach(s->{
                            totalSearchHavestResponse.add(s);
                        });
                    }
                }
            }*/


            if(pageResult.getList() != null && pageResult.getList().size() >0){
                List<RelateHavestDTO> relateHavests = mapperFacade.mapAsList(pageResult.getList(), RelateHavestDTO.class);

                if(CollectionUtils.isNotEmpty(relateHavests)){
                    relateHavests.stream().forEach(s->{
                        StringBuffer sb = new StringBuffer();
                        // 技术领域
                        if(s.getTechDomain1() != null){
                            TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain1()));
                            if(tfc1 != null){
                                sb.append(tfc1.getName()).append(",");
                            }
                        }
                        if(s.getTechDomain2() != null){
                            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain2()));
                            if(tfc2 != null){
                                sb.append(tfc2.getName()).append(",");
                            }
                        }

                        if(s.getTechDomain() != null){
                            TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain()));
                            if(tfc != null){
                                sb.append(tfc.getName()).append(",");
                            }
                        }
                        s.setTechDomainName(sb.toString());
                    });
                }

                relateDTO.setHavests(relateHavests);
            }


            // 三个技术经纪人
            TechEconomicManRequest techEconomicManRequest = new TechEconomicManRequest();
            techEconomicManRequest.setPageNum(1);
            techEconomicManRequest.setPageSize(3);
            // techEconomicManRequest.setSearchFiled(label);
            PageResult<TechEconomicMan> pages = techEconomicManService.pageQuery(techEconomicManRequest);
            if(pages.getList() != null && pages.getList().size() >0){
                List<TechEconomicManRecommondDTO> economics = mapperFacade.mapAsList(pages.getList(), TechEconomicManRecommondDTO.class);
                economics.stream().forEach(s->{
                    s.setLevelName(LevelEnum.getNameByIndex(s.getLevel()));
                });

                relateDTO.setEconomics(economics);
            }
        }

        return relateDTO;
    }


}
