package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.client.TechEconomicManAppraiseApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.MessageLogInfo;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.enums.UserTypeEnum;
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
import java.util.stream.Collectors;

@Api(tags = "用户中心：我的发布榜单")
@RestController
@Slf4j
@ResponseBody
public class MyPublishBillboardController extends BaseController {
    @Resource
    BillboardFeignApi billboardFeignApi;
    @Resource
    BillboardGainFeignApi billboardGainFeignApi;
    @Resource
    TechEconomicManAppraiseApi techEconomicManAppraiseApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    BillboardEconomicRelatedFeignApi billboardEconomicRelatedFeignApi;
    @Resource
    BillboardTalentRelatedFeignApi billboardTalentRelatedFeignApi;
    @Resource
    BillboardHarvestRelatedFeignApi billboardHarvestRelatedFeignApi;
    @Resource
    MessageFeignApi messageFeignApi;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;



    @ApiOperation("获取上传成功后榜单列表")
    @PostMapping("/user/center/billboard/pageQuery")
    PageResult<BillboardResponse> pageQuery(@RequestBody BillboardRequest request){
        Long createBy = this.getUserId();
        if(createBy == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        request.setCreateBy(createBy);

        PageResult<BillboardResponse> pageResult = billboardFeignApi.pageQuery(request);

        List<BillboardResponse> response = pageResult.getList();
        List<Long> ids = new ArrayList<>();
        List<Long> finalIds = ids;

        // 创建者
        List<Long> idCs = new ArrayList<>();
        List<Long> finalIdCs = idCs;
        if(response != null){
            response.stream().forEach(s->{
                if(s.getCreateBy() != null){
                    finalIdCs.add(s.getCreateBy());
                }
                if(s.getAuditUserId() != null){
                    finalIds.add(s.getAuditUserId());
                }
            });

            // 去重
            idCs = finalIdCs.stream().distinct().collect(Collectors.toList());
            Long[] createByIds = new Long[idCs.size()];
            idCs .toArray(createByIds);
            List<UserResponse> createByResponses = userFeignApi.getUserByIds(createByIds);
            response.forEach(s->{
                if(s.getCreateBy() != null){
                    UserResponse u = createByResponses.stream()
                            .filter(user -> s.getCreateBy().equals(user.getId()))
                            .findAny()
                            .orElse(null);
                    if(u != null){
                        s.setCreateByName(u.getNick());
                    }
                }
            });


            // 去重
            ids = finalIds.stream().distinct().collect(Collectors.toList());
            Long[] userIds = new Long[ids.size()];
            ids.toArray(userIds);
            if(CollectionUtils.isNotEmpty(ids)){
                List<UserResponse> userResponses = userFeignApi.getUserByIds(userIds);
                if(CollectionUtils.isNotEmpty(userResponses)){
                    response.forEach(s->{
                        if(s.getAuditUserId() != null){
                            UserResponse u = userResponses.stream()
                                    .filter(user -> s.getAuditUserId().equals(user.getId()))
                                    .findAny()
                                    .orElse(null);
                            if(u != null){
                                s.setAuditUserName(u.getNick());
                            }
                        }
                    });
                }
            }
        }

        return pageResult;
    }



    @ApiOperation("获取我发布的榜单列表")
    @PostMapping("/user/center/queryMyPublish")
    MyPublishBillboardResponse queryMyPublish(@RequestBody MyPublishBillboardRequest request){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        request.setUserId(this.getUserId());
        MyPublishBillboardResponse response = billboardFeignApi.queryMyPublish(request);
        List<MyPublishBillboardInfo> billboards = response.getBillboards();
        if(CollectionUtils.isNotEmpty(billboards)){
            billboards.stream().forEach(s->{
                s.setStatusName(BillboardStatusEnum.getNameByIndex(s.getStatus()));
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeEnum.categories.name(),String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
            });
        }

        return response;
    }

    @ApiOperation(value = "删除我发布的榜单", notes = "删除我发布的榜单")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/user/center/deleteMyBillboard")
    public void deleteMyBillboard(@RequestParam("id") Long id){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        Long[] ids = {id};
        billboardFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation(value = "批量删除榜单", notes = "批量删除榜单")
    @PostMapping("/user/center/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        billboardFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("我要发榜")
    @PostMapping("/user/center/addMyBillboard")
    void addMyBillboard(@RequestBody BillboardDTO billboardDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }


        // 判断是否政府发布政府榜单
        UserResponse userResponse = getUser();
        // 如果榜单是政府榜单，但用户又不是政府部门，那么抛出异常
        if(!"admin".equalsIgnoreCase(userResponse.getMobile())){
            if(billboardDTO.getType().equals(BillboardTypeEnum.GOV_BILLBOARD.getCode()) && !userResponse.getUnitNature().equals(UserTypeEnum.GOV.getCode())){
                throw new BizException(BusinessErrorCode.GOV_BILLBOARD_PUBLISH_ERROR);
            }else if(billboardDTO.getType().equals(BillboardTypeEnum.BUS_BILLBOARD.getCode())){
                boolean flag = false;
                if(userResponse.getUnitNature().equals(UserTypeEnum.BUZ.getCode())
                        || userResponse.getUnitNature().equals(UserTypeEnum.TEAM.getCode())
                        || userResponse.getUnitNature().equals(UserTypeEnum.EDU.getCode()) ){
                    flag = true;
                }
                if(!flag){
                    throw new BizException(BusinessErrorCode.NO_QL_PUBLISH_ERROR);
                }
            }
        }

        billboardDTO.setCreateBy(userId);
        billboardDTO.setCreateAt(new Date());
        // 设置默认的待揭榜
        billboardDTO.setStatus(BillboardStatusEnum.WAIT.getCode());

        if(StrUtil.isNotEmpty(billboardDTO.getPublishPerson())){
            billboardDTO.setUnitName(billboardDTO.getPublishPerson());
        }else if(userResponse != null){
            billboardDTO.setUnitName(userResponse.getUnitName());
        }

        // 如果是企业榜，还要有logo
        if(BillboardTypeEnum.BUS_BILLBOARD.getCode().equals(billboardDTO.getType())){
            if(userResponse != null){
                billboardDTO.setUnitName(userResponse.getUnitName());
                billboardDTO.setUnitLogo(userResponse.getUnitLogo());
            }
        }


        billboardFeignApi.add(billboardDTO);
    }

    @ApiOperation("编辑我发布的榜单信息")
    @PostMapping("/user/center/updateMyBillboard")
    void updateMyBillboard(@RequestBody BillboardDTO billboardDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        if(billboardDTO.getId() == null){
            throw new BizException(BusinessErrorCode.BUSINESS_PARAMS_ERROR);
        }
        billboardFeignApi.updateMyBillboard(billboardDTO);
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/user/center/myBillboardDetail")
    public DetailInfoDTO myBillboardDetail(@RequestParam("id")Long id){
        DetailInfoDTO detailInfo = billboardFeignApi.detail(id);

        // 获取揭榜
        List<BillboardGainDTO> billboardGainResponses = billboardGainFeignApi.getBillboardGainByPid(id);
        if(CollectionUtils.isNotEmpty(billboardGainResponses)){
            billboardGainResponses.stream().forEach(s->{
                s.setAuditingStatusName(AuditingStatusEnum.getNameByIndex(s.getAuditingStatus()));
            });
        }
        detailInfo.setBillboardGains(billboardGainResponses);
        detailInfo.setStatusName(BillboardStatusEnum.getNameByIndex(detailInfo.getStatus()));

        // 成果推荐
        List<BillboardHarvestRelatedResponse> havests = billboardHarvestRelatedFeignApi.getHarvestRecommend(id);
        detailInfo.setHarvestRecommends(havests);

        // 帅才推荐： 根据技术领域，研究方向确定
        List<BillboardTalentRelatedResponse> talentRecommends = billboardTalentRelatedFeignApi.getTalentRecommend(id);
        if(CollectionUtils.isNotEmpty(talentRecommends)){
            talentRecommends.stream().forEach(s->{
                // 学历显示名称
                DictionaryDTO edu = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.highest_edu.name(),  s.getHighestEdu());
                if(edu != null){
                    s.setHighestEduName(edu.getDicValue());
                }


                StringBuffer sb = new StringBuffer();
                if(s.getTechDomain1() != null && !"".equals(s.getTechDomain1())){
                    TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain1()));
                    if(tfc1 != null){
                        s.setTechDomain1Name(tfc1.getName());
                        sb.append(tfc1.getName()).append(CharUtil.COMMA);
                    }
                }
                if(s.getTechDomain2() != null  && !"".equals(s.getTechDomain2())){
                    TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain2()));
                    if(tfc2 != null){
                        s.setTechDomain2Name(tfc2.getName());
                        sb.append(tfc2.getName()).append(CharUtil.COMMA);
                    }
                }
                if(s.getTechDomain() != null  && !"".equals(s.getTechDomain())){
                    TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain()));
                    if(tfc != null){
                        s.setTechDomainName(tfc.getName());
                        sb.append(tfc.getName()).append(CharUtil.COMMA);
                    }
                }

                String temp = sb.toString().substring(0, sb.toString().length()-1);
                s.setTechDomainName(sb.toString());

            });
        }
        detailInfo.setTalentRecommends(talentRecommends);

        // 技术经纪人推荐: 根据专业标签来推荐 (这里是已经派单确认得经纪人)
        List<BillboardEconomicRelatedResponse> techBrokerRecommends = billboardEconomicRelatedFeignApi.getEconomicRecommend(id);
        if(CollectionUtils.isNotEmpty(techBrokerRecommends)){
            BillboardEconomicRelatedResponse relatedResponse = techBrokerRecommends.get(0);
            detailInfo.setAppTechBrokerRecommends(relatedResponse);
        }

        MyBillboardEconomicManDTO economicMan = billboardEconomicRelatedFeignApi.getMyEconomicMan(id);
        detailInfo.setMyBillboardEconomicMan(economicMan);

        return detailInfo;
    }

    @ApiOperation("我的技术经纪人评分")
    @PostMapping("/user/center/addAppraise")
    void addAppraise(@RequestBody TechEconomicManAppraiseDTO techEconomicManAppraiseDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        techEconomicManAppraiseDTO.setCreateAt(new Date());
        techEconomicManAppraiseDTO.setCreateBy(userId);
        if(techEconomicManAppraiseDTO.isAnonymous()){
            // 匿名发表评价
            techEconomicManAppraiseDTO.setName(null);
        }else{
            UserResponse userResponse = getUser();
            if(userResponse != null){
                techEconomicManAppraiseDTO.setName(userResponse.getNick());
            }
        }

        techEconomicManAppraiseApi.add(techEconomicManAppraiseDTO);
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


    @ApiOperation("揭榜方案评审提交")
    @PostMapping("/user/center/publish/billboard/auditing")
    public void update(@RequestBody BillboardGainAuditDTO billboardGainAuditDTO) throws BizException {
        billboardGainAuditDTO.setAuditingUserId(this.getUserId());
        UserResponse userResponse = getUser();
        if(userResponse != null){
            billboardGainAuditDTO.setAuditingUserName(userResponse.getNick());
        }

        billboardGainFeignApi.update(billboardGainAuditDTO);

        // 写消息： 你的%s揭榜方案已经审核%s
        BillboardGainDTO billboardGainDTO =  billboardGainFeignApi.getBillboardGainById(billboardGainAuditDTO.getId());
        BillboardDTO billboardDTO = billboardFeignApi.getById(billboardGainDTO.getPid());
        // 写系统消息
        MessageDTO messageDTO = new MessageDTO();
        // 时间
        messageDTO.setCreateAt(new Date());
        // 内容
        String content = String.format(MessageLogInfo.billboard_jb_fa_auth,
                billboardDTO.getTitle(), AuditingStatusEnum.getNameByIndex(billboardGainAuditDTO.getAuditingStatus()));
        messageDTO.setContent(content);
        // 揭榜人
        messageDTO.setUserId(billboardGainDTO.getCreateBy());
        messageDTO.setTitle(content);
        // 系统消息
        messageDTO.setType(0);
        // 榜单ID
        messageDTO.setPid(billboardDTO.getId());
        messageFeignApi.add(messageDTO);
    }


}
