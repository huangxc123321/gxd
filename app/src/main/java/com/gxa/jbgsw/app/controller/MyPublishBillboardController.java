package com.gxa.jbgsw.app.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.app.feignapi.*;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.client.TechEconomicManAppraiseApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.MessageLogInfo;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @ApiOperation("我要发榜")
    @PostMapping("/user/center/addMyBillboard")
    void addMyBillboard(@RequestBody BillboardDTO billboardDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        billboardDTO.setCreateBy(userId);
        billboardDTO.setCreateAt(new Date());
        // 设置默认的待揭榜
        billboardDTO.setStatus(0);
        if(billboardDTO.getUnitName() == null){
            billboardDTO.setUnitName(this.getUnitName());
        }

        // 判断是否政府发布政府榜单
        // TODO: 2023/7/7 0007 现在的流程暂时不用判断

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

        // 成果推荐: 根据揭榜单位
        List<BillboardHarvestRelatedResponse> havests = billboardHarvestRelatedFeignApi.getHarvestRecommend(id);
        detailInfo.setHarvestRecommends(havests);

        // 帅才推荐： 根据技术领域，研究方向确定
        List<BillboardTalentRelatedResponse> talentRecommends = billboardTalentRelatedFeignApi.getTalentRecommend(id);
        detailInfo.setTalentRecommends(talentRecommends);

        // 技术经纪人推荐: 根据专业标签来推荐
        List<BillboardEconomicRelatedResponse> techBrokerRecommends = billboardEconomicRelatedFeignApi.getEconomicRecommend(id);
        detailInfo.setTechBrokerRecommends(techBrokerRecommends);

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
