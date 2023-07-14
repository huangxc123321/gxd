package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.IndexApi;
import com.gxa.jbgsw.business.entity.Billboard;
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
    MapperFacade mapperFacade;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    HarvestService harvestService;
    @Resource
    TalentPoolService talentPoolService;
    @Resource
    TechEconomicManService techEconomicManService;
    @Resource
    NewsService newsService;

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


}
