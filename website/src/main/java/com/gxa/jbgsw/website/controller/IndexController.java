package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.basis.protocol.enums.BannerTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "首页管理")
@RestController
@Slf4j
@ResponseBody
public class IndexController extends BaseController {
    @Resource
    IndexFeignApi indexFeignApi;
    @Resource
    BannerFeignApi bannerFeignApi;
    @Resource
    BillboardFeignApi billboardFeignApi;
    @Resource
    HavestFeignApi havestFeignApi;
    @Resource
    TalentPoolFeignApi talentPoolFeignApi;
    @Resource
    TechEconomicManFeignApi techEconomicManFeignApi;
    @Resource
    BillboardGainFeignApi billboardGainFeignApi;
    @Resource
    NewsFeignApi newsFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    WebsiteBottomFeignApi websiteBottomFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;


    @ApiOperation("获取首页榜单信息")
    @GetMapping("/index/getIndex")
    public ApiResult<IndexResponse> getIndex() {
        IndexResponse indexResponse = indexFeignApi.getIndex();
        ApiResult apiResult = new ApiResult<IndexResponse>();
        apiResult.setData(indexResponse);

        return apiResult;
    }

    @ApiOperation("获取首页轮播图")
    @GetMapping("/index/getIndexBanners")
    ApiResult<BannerResponse> getIndexBanners() {
        List<BannerResponse> bannerResponses = bannerFeignApi.getIndexBanners(BannerTypeEnum.PC.getCode());
        ApiResult apiResult = new ApiResult<BannerResponse>();
        apiResult.setData(bannerResponses);

        return apiResult;
    }

    @ApiOperation("搜索政府榜")
    @PostMapping("/index/searchGovBillborads")
    PageResult<BillboardIndexDTO> searchGovBillborads(@RequestBody SearchGovRequest searchGovRequest) {
        searchGovRequest.setType(BillboardTypeEnum.GOV_BILLBOARD.getCode());
        return indexFeignApi.queryGovBillborads(searchGovRequest);
    }

    @ApiOperation("获取某个政府榜信息")
    @GetMapping("/index/getGovBillboradById")
    DetailInfoDTO getGovBillboradById(@RequestParam(value = "id") Long id) {
        return billboardFeignApi.detail(id);
    }

    @ApiOperation("根据榜单ID获取相关成果、帅才推荐、榜单推荐信息， （榜单详情页使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByBillboardId")
    RelateDTO getRelatedByBillboardId(@RequestParam(value = "id") Long id) {
        RelateDTO relateDTO = indexFeignApi.getRelatedByBillboardId(id);
        return relateDTO;
    }

    @ApiOperation("根据成果ID获取相关成果、帅才推荐、榜单推荐信息， （成果详情页使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "成果ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByHarvestId")
    RelateDTO getRelatedByHarvestId(@RequestParam(value = "id") Long id) {
        RelateDTO relateDTO = indexFeignApi.getRelatedByHarvestId(id);
        return relateDTO;
    }

    @ApiOperation("根据帅才ID获取相关成果、帅才推荐、榜单推荐信息， （帅才详情页使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "帅才ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByTalentId")
    RelateDTO getRelatedByTalentId(@RequestParam(value = "id") Long id) {
        RelateDTO relateDTO = indexFeignApi.getRelatedByTalentId(id);
        return relateDTO;
    }


    @ApiOperation("搜索企业榜")
    @PostMapping("/index/searchBizBillborads")
    PageResult<BillboardIndexDTO> searchBizBillborads(@RequestBody SearchBizRequest searchGovRequest) {
        searchGovRequest.setType(BillboardTypeEnum.BUS_BILLBOARD.getCode());
        return indexFeignApi.queryBizBillborads(searchGovRequest);
    }

    @ApiOperation("搜索成果")
    @PostMapping("/index/searchHarvests")
    PageResult<SearchHavestResponse> searchHarvests(@RequestBody SearchHarvestsRequest searchHarvestsRequest) {
        PageResult<SearchHavestResponse> pageResult = indexFeignApi.queryHarvests(searchHarvestsRequest);
        List<SearchHavestResponse> responses = pageResult.getList();
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                DictionaryDTO dicMaturityLevel = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.maturity_level.name(), String.valueOf(s.getMaturityLevel()));
                if(dicMaturityLevel != null){
                    s.setMaturityLevelName(dicMaturityLevel.getDicValue());
                }
            });
        }

        return pageResult;
    }
    @ApiOperation("获取某个成果信息")
    @GetMapping("/index/getHavestById")
    DetailInfoDTO getHavestById(@RequestParam(value = "id") Long id) {
        return havestFeignApi.detail(id);
    }

    @ApiOperation("搜索帅才")
    @PostMapping("/index/searchTalents")
    PageResult<SearchTalentsResponse> searchTalents(@RequestBody SearchTalentsRequest searchTalentsRequest) {
        PageResult<SearchTalentsResponse> pageResult = indexFeignApi.queryTalents(searchTalentsRequest);
        List<SearchTalentsResponse> responses = pageResult.getList();
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                if(dicProfessional != null){
                    s.setProfessionalName(dicProfessional.getDicValue());
                }
            });
        }

        return pageResult;
    }
    @ApiOperation("获取某个帅才信息")
    @GetMapping("/index/getTalentById")
    TalentPoolDTO getTalentById(@RequestParam(value = "id") Long id) {
        return talentPoolFeignApi.getTalentPoolById(id);
    }

    @ApiOperation("搜索技术经纪人")
    @PostMapping("/index/searchEconomicMans")
    PageResult<SearchEconomicMansResponse> searchEconomicMans(@RequestBody SearchEconomicMansRequest searchTalentsRequest) {
        return indexFeignApi.queryEconomicMans(searchTalentsRequest);
    }
    @ApiOperation("获取某个技术经纪人信息")
    @GetMapping("/index/getEconomicManById")
    TechEconomicManResponse getEconomicManById(@RequestParam(value = "id") Long id) {
        return techEconomicManFeignApi.getTechEconomicManById(id);
    }

    @ApiOperation("搜索新闻资讯")
    @PostMapping("/index/searchNews")
    PageResult<SearchNewsResponse> searchNews(@RequestBody SearchNewsRequest searchNewsRequest) {
        PageResult<SearchNewsResponse> pageResult = indexFeignApi.queryNews(searchNewsRequest);
        List<SearchNewsResponse> responses = pageResult.getList();
        if(responses != null && responses.size()> 0){
            responses.stream().forEach(s->{

            });
        }

        return pageResult;
    }
    @ApiOperation("获取某个新闻资讯")
    @GetMapping("/index/getNewsById")
    NewsDTO getNewsById(@RequestParam(value = "id") Long id) {
        return newsFeignApi.detail(id);
    }

    @ApiOperation("立即揭榜")
    @PostMapping("/index/addBillboardGain")
    void addBillboardGain(@RequestBody BillboardGainAddDTO billboardGainAddDTO) throws BizException {
        billboardGainAddDTO.setApplyAt(new Date());
        UserResponse userResponse = getUser();
        if(userResponse != null){
            billboardGainAddDTO.setCreateByName(userResponse.getNick());
            billboardGainAddDTO.setAcceptBillboard(userResponse.getUnitName());
            billboardGainAddDTO.setCreateBy(userResponse.getCreateBy());
            billboardGainAddDTO.setCreateAt(new Date());
        }
        billboardGainFeignApi.addBillboardGain(billboardGainAddDTO);
    }

    @ApiOperation("获取网站底部信息")
    @GetMapping("/website/bottom/getWebsiteBottomInfo")
    WebsiteBottomDTO getWebsiteBottomInfo() {
        return websiteBottomFeignApi.getWebsiteBottomInfo();
    }


    private UserResponse getUser(){
        Long userId = this.getUserId();
        String userInfo = stringRedisTemplate.opsForValue().get(RedisKeys.USER_INFO+userId);
        if(StrUtil.isBlank(userInfo)){
            throw new BizException(UserErrorCode.LOGIN_CODE_ERROR);
        }
        UserResponse userResponse = JSONObject.parseObject(userInfo, UserResponse.class);
        return userResponse;
    }

}
