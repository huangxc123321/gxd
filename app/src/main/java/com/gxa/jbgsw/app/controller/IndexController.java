package com.gxa.jbgsw.app.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.app.feignapi.*;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.basis.protocol.enums.BannerTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.*;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    @Resource
    CollaborateFeignApi collaborateFeignApi;
    @Resource
    MessageFeignApi messageFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");


    @ApiOperation("获取最新的榜单信息")
    @GetMapping("/index/searchNew")
    public List<BillboardResponse> searchNew(@RequestParam("num") Integer num) {
        return indexFeignApi.searchNew(num);
    }

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

    @ApiOperation("获取某个榜单信息")
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

        // 是否或者
        if(userId != null){
            CollaborateDTO collaborateDTO = collaborateFeignApi.getCollaborateInfo(userId, id);
            if(collaborateDTO != null){
                havestDetailInfo.setCollaborate(true);
            }
        }
        apiResult.setData(havestDetailInfo);

        return apiResult;
    }

    @ApiOperation("搜索帅才")
    @PostMapping("/index/searchTalents")
    PageResult<SearchTalentsResponse> searchTalents(@RequestBody SearchTalentsRequest searchTalentsRequest) {
        PageResult<SearchTalentsResponse> pageResult = indexFeignApi.queryTalents(searchTalentsRequest);

        if(pageResult != null){
            List<SearchTalentsResponse> responses = pageResult.getList();
            if(CollectionUtils.isNotEmpty(responses)){
                responses.stream().forEach(s->{
                    DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                    if(dicProfessional != null){
                        s.setProfessionalName(dicProfessional.getDicValue());
                    }

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
        // 判断是否合作
        if(userId != null){
            CollaborateDTO collaborateDTO = collaborateFeignApi.getCollaborateInfo(userId, id);
            if(collaborateDTO != null){
                telentPoolDTO.setCollaborate(true);
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

    @ApiOperation("搜索团队")
    @PostMapping("/index/searchTeams")
    PageResult<SearchTeamsResponse> searchTeams(@RequestBody CompanyRequest companyRequest) {
        return indexFeignApi.searchTeamRequest(companyRequest);
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
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

       try{
           billboardGainAddDTO.setApplyAt(new Date());
           UserResponse userResponse = getUser();
           if(userResponse != null){
               billboardGainAddDTO.setCreateByName(userResponse.getNick());
               billboardGainAddDTO.setAcceptBillboard(userResponse.getUnitName());
               billboardGainAddDTO.setCreateBy(userId);
               billboardGainAddDTO.setCreateAt(new Date());
               billboardGainFeignApi.addBillboardGain(billboardGainAddDTO);

               // 写消息（立即揭榜： （用户名）在XXX时间揭榜了您的XXXX榜单）
               UserResponse u = this.getUser();
               BillboardDTO billboardDTO = billboardFeignApi.getById(billboardGainAddDTO.getPid());
               // 写系统消息
               MessageDTO messageDTO = new MessageDTO();
               // 时间
               messageDTO.setCreateAt(new Date());
               String time = simpleDateFormat.format(new Date());
               // 内容
               String content = String.format(MessageLogInfo.billboard_jb, userResponse.getNick(),
                       time, billboardDTO.getTitle());

               messageDTO.setContent(content);
               // 榜单发布人
               messageDTO.setUserId(billboardDTO.getCreateBy());
               messageDTO.setTitle(content);
               // 立即揭榜
               messageDTO.setType(0);
               messageDTO.setThirdAvatar(u.getAvatar());
               messageDTO.setThirdName(u.getNick());

               // 榜单ID
               messageDTO.setPid(billboardDTO.getId());
               messageFeignApi.add(messageDTO);
           }
       }catch (Exception ex){
           ex.printStackTrace();
       }

    }

    @ApiOperation("获取网站底部信息")
    @GetMapping("/website/bottom/getWebsiteBottomInfo")
    WebsiteBottomDTO getWebsiteBottomInfo() {
        return websiteBottomFeignApi.getWebsiteBottomInfo();
    }


    @ApiOperation("收藏（榜单，成果，政策，帅才）")
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
