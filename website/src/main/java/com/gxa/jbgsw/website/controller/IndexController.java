package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.basis.protocol.enums.BannerTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.*;
import io.swagger.annotations.Api;
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
    @Resource
    CollectionFeignApi collectionFeignApi;
    @Resource
    AttentionFeignApi attentionFeignApi;
    @Resource
    TechEconomicManAppraiseFeignApi techEconomicManAppraiseFeignApi;



    @ApiOperation("获取首页榜单信息")
    @PostMapping("/index/search")
    public PcIndexSearchResponse search(@RequestBody PcIndexSearchRequest pcIndexSearchRequest) {
        PcIndexSearchResponse pcIndexSearchResponse = new PcIndexSearchResponse();

        return indexFeignApi.search(pcIndexSearchRequest);
    }


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
        DetailInfoDTO detailInfoDTO = billboardFeignApi.detail(id);
        // 判断是否收藏
        Long userId = this.getUserId();
        if(userId != null){
            CollectionDTO collectionDTO = null;
            Integer collectionType = 0;
            if(CollectionTypeEnum.GOV.getCode().equals(detailInfoDTO.getType())){
                collectionType = CollectionTypeEnum.GOV.getCode();
            }else if(CollectionTypeEnum.BUZ.getCode().equals(detailInfoDTO.getType())){
                collectionType = CollectionTypeEnum.BUZ.getCode();
            }

            collectionDTO = collectionFeignApi.getCollection(id, userId, collectionType);

            if(collectionDTO != null){
                detailInfoDTO.setCollectionStatus(CollectionStatusEnum.COLLECTION.getCode());
            }
        }

        detailInfoDTO.setStatusName(BillboardStatusEnum.getNameByIndex(detailInfoDTO.getStatus()));


        return detailInfoDTO;
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
    ApiResult<HavestDetailInfo> getHavestById(@RequestParam(value = "id") Long id) {
        ApiResult<HavestDetailInfo>  apiResult = new ApiResult<>();

        HavestDetailInfo havestDetailInfo = havestFeignApi.detail(id);
        // 判断是否收藏
        Long userId = this.getUserId();
        if(userId != null){
            CollectionDTO collectionDTO = null;
            // 收藏： 成果
            Integer collectionType = CollectionTypeEnum.HAVEST.getCode();

            collectionDTO = collectionFeignApi.getCollection(id, userId, collectionType);
            if(collectionDTO != null){
                havestDetailInfo.setCollectionStatus(CollectionStatusEnum.COLLECTION.getCode());
            }
        }
        apiResult.setData(havestDetailInfo);

        return apiResult;
    }

    @ApiOperation("搜索帅才")
    @PostMapping("/index/searchTalents")
    PageResult<SearchTalentsResponse> searchTalents(@RequestBody SearchTalentsRequest searchTalentsRequest) {
        PageResult<SearchTalentsResponse> pageResult = indexFeignApi.queryTalents(searchTalentsRequest);

        if(pageResult == null){
            List<SearchTalentsResponse> responses = pageResult.getList();
            if(CollectionUtils.isNotEmpty(responses)){
                responses.stream().forEach(s->{
                    DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                    if(dicProfessional != null){
                        s.setProfessionalName(dicProfessional.getDicValue());
                    }
                });
            }

        }

        return pageResult;
    }
    @ApiOperation("获取某个帅才信息")
    @GetMapping("/index/getTalentPoolDetailInfo")
    ApiResult<TalentPoolDetailInfo> getTalentPoolDetailInfo(@RequestParam(value = "id") Long id) {
        ApiResult<TalentPoolDetailInfo> apiResult = new ApiResult<>();

        TalentPoolDetailInfo telentPoolDTO = talentPoolFeignApi.getTalentPoolDetailInfo(id);
        // 判断是否收藏
        Long userId = this.getUserId();
        if(userId != null){
            AttentionDTO attentionDTO = null;
            // 关注： 帅才
            Integer attentionType = AttentionTypeEnum.TALENT.getCode();

            AttentionDTO atInfo = attentionFeignApi.getAttention(id, userId, attentionType);
            if(atInfo != null){
                telentPoolDTO.setAttentionStatus(AttentionStatusEnum.ATTENTION.getCode());
            }
        }
        apiResult.setData(telentPoolDTO);

        return apiResult;
    }

    @ApiOperation("搜索技术经纪人")
    @PostMapping("/index/searchEconomicMans")
    PageResult<SearchEconomicMansResponse> searchEconomicMans(@RequestBody SearchEconomicMansRequest searchTalentsRequest) {
        return indexFeignApi.queryEconomicMans(searchTalentsRequest);
    }
    @ApiOperation("获取某个技术经纪人信息")
    @GetMapping("/index/getEconomicManById")
    TechEconomicManResponse getEconomicManById(@RequestParam(value = "id") Long id) {
        TechEconomicManResponse techEconomicManResponse = techEconomicManFeignApi.getTechEconomicManById(id);

        // 判断是否收藏
        Long userId = this.getUserId();
        if(userId != null){
            AttentionDTO attentionDTO = null;
            // 关注： 帅才
            Integer attentionType = AttentionTypeEnum.TECH.getCode();

            AttentionDTO atInfo = attentionFeignApi.getAttention(id, userId, attentionType);
            if(atInfo != null){
                techEconomicManResponse.setAttentionStatus(AttentionStatusEnum.ATTENTION.getCode());
            }
        }

        return techEconomicManResponse;
    }

    @ApiOperation("获取技术经纪人的评价")
    @PostMapping("/tech/economic/appraise/getAppraise")
    PageResult<TechEconomicManAppraiseResponse> getAppraise(@RequestBody TechEconomicManAppraiseRequest request) {
        return techEconomicManAppraiseFeignApi.getAppraise(request);
    }

    @ApiOperation("搜索新闻资讯")
    @PostMapping("/index/searchNews")
    PageResult<SearchNewsResponse> searchNews(@RequestBody SearchNewsRequest searchNewsRequest) {
        PageResult<SearchNewsResponse> pageResult = indexFeignApi.queryNews(searchNewsRequest);
        List<SearchNewsResponse> responses = pageResult.getList();
        if(responses != null && responses.size()> 0){
            responses.stream().forEach(s->{
                s.setTypeName(NewsTypeEnum.getNameByIndex(s.getType()));
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

    @ApiOperation("榜单收藏")
    @PostMapping("/collection/add")
    void addCollection(@RequestBody CollectionDTO collectionDTO){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        collectionDTO.setCreateAt(new Date());
        collectionDTO.setUserId(this.getUserId());

        if(CollectionStatusEnum.COLLECTION.getCode().equals(collectionDTO.getStatus())){
            collectionFeignApi.add(collectionDTO);
        }else {
            collectionFeignApi.delete(collectionDTO);
        }
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
