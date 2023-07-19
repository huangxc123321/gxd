package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.basis.protocol.enums.BannerTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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


    @ApiOperation("获取首页榜单信息")
    @GetMapping("/index/getIndex")
    public IndexResponse getIndex() {
        return indexFeignApi.getIndex();
    }

    @ApiOperation("获取首页轮播图")
    @GetMapping("/index/getIndexBanners")
    List<BannerResponse> getIndexBanners() {
        return bannerFeignApi.getIndexBanners(BannerTypeEnum.PC.getCode());
    }

    @ApiOperation("搜索政府榜")
    @PostMapping("/index/searchGovBillborads")
    PageResult<BillboardIndexDTO> searchGovBillborads(@RequestBody SearchGovRequest searchGovRequest) {
        return indexFeignApi.queryGovBillborads(searchGovRequest);
    }

    @ApiOperation("获取某个政府榜信息")
    @GetMapping("/index/getGovBillboradById")
    DetailInfoDTO getGovBillboradById(@RequestParam(value = "id") Long id) {
        return billboardFeignApi.detail(id);
    }

    @ApiOperation("搜索企业榜")
    @PostMapping("/index/searchBizBillborads")
    PageResult<BillboardIndexDTO> searchBizBillborads(@RequestBody SearchBizRequest searchGovRequest) {
        return indexFeignApi.queryBizBillborads(searchGovRequest);
    }

    @ApiOperation("搜索成果")
    @PostMapping("/index/searchHarvests")
    PageResult<SearchHavestResponse> searchHarvests(@RequestBody SearchHarvestsRequest searchHarvestsRequest) {
        return indexFeignApi.queryHarvests(searchHarvestsRequest);
    }
    @ApiOperation("获取某个成果信息")
    @GetMapping("/index/getHavestById")
    DetailInfoDTO getHavestById(@RequestParam(value = "id") Long id) {
        return havestFeignApi.detail(id);
    }

    @ApiOperation("搜索帅才")
    @PostMapping("/index/searchTalents")
    PageResult<SearchTalentsResponse> searchTalents(@RequestBody SearchTalentsRequest searchTalentsRequest) {
        return indexFeignApi.queryTalents(searchTalentsRequest);
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
        return indexFeignApi.queryNews(searchNewsRequest);
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
