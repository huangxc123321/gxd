package com.gxa.jbgsw.website.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.CollaborateTypeEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.*;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "我的合作管理")
@RestController
@Slf4j
public class MyCollaborateController extends BaseController {
    @Resource
    CollaborateFeignApi collaborateFeignApi;
    @Resource
    BillboardTalentRelatedFeignApi billboardTalentRelatedFeignApi;
    @Resource
    HavestFeignApi havestFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    TalentPoolFeignApi talentPoolFeignApi;
    @Resource
    BillboardFeignApi billboardFeignApi;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    MessageFeignApi messageFeignApi;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    StringRedisTemplate stringRedisTemplate;


    @ApiOperation("合作申请(别人发起)")
    @PostMapping("/collaborate/apply")
    void apply(@RequestBody MyCollaborateApplyDTO collaborateApply) throws BizException {
        collaborateFeignApi.apply(collaborateApply);
    }


    @ApiOperation("需求合作查看")
    @GetMapping("/collaborate/getTalentCollaborateDetail")
    CollaborateTaleDetailDTO getTalentCollaborateDetail(@RequestParam("id") Long id){
        CollaborateDTO collaborateDTO = collaborateFeignApi.getById(id);
        CollaborateTaleDetailDTO collaborateTaleDetailDTO = mapperFacade.map(collaborateDTO, CollaborateTaleDetailDTO.class);

        // 获取帅才信息
        TalentPoolDTO talentPoolDTO = talentPoolFeignApi.getTalentPoolById(collaborateDTO.getPid());
        if(talentPoolDTO != null){
            collaborateTaleDetailDTO.setName(talentPoolDTO.getName());
            collaborateTaleDetailDTO.setPhoto(talentPoolDTO.getPhoto());
            collaborateTaleDetailDTO.setUnitName(talentPoolDTO.getUnitName());
            collaborateTaleDetailDTO.setProfessionalName(talentPoolDTO.getProfessionalName());
            collaborateTaleDetailDTO.setTechDomainName(talentPoolDTO.getTechDomainName());
            collaborateTaleDetailDTO.setResearchDirection(talentPoolDTO.getResearchDirection());
        }

        // 获取邀请榜的信息
        if(collaborateDTO.getBillboardId() != null){
            Long[] ids = new Long[1];
            ids[0] = collaborateDTO.getBillboardId();
            List<BillboardDTO> billboards = billboardFeignApi.batchQueryByIds(ids);
            List<MyBillboradCollaborateResponse> list = mapperFacade.mapAsList(billboards, MyBillboradCollaborateResponse.class);
            collaborateTaleDetailDTO.setBillboards(list);
        }

        return collaborateTaleDetailDTO;
    }

    @ApiOperation("成果合作查看")
    @GetMapping("/collaborate/getHarvestCollaborateDetail")
    CollaborateHavrestDetailDTO getHarvestCollaborateDetail(@RequestParam("id") Long id){
        return collaborateFeignApi.getHarvestCollaborateDetail(id);
    }

    @ApiOperation("取消合作")
    @GetMapping("/collaborate/cancel")
    void cancel(@RequestParam("id") Long id) throws BizException {
        collaborateFeignApi.cancel(id);
    }

    @ApiOperation("发起合作")
    @PostMapping("/collaborate/add")
    void add(@RequestBody CollaborateDTO collaborateDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        Long harvestUserId = null;

        collaborateDTO.setEffectAt(new Date());
        collaborateDTO.setLaunchUserId(userId);
        collaborateDTO.setLaunchUserName(this.getUserNick());
        // 合作类型：0 成果合作  1 需求合作（跟帅才合作）
        if(CollaborateTypeEnum.GAIN.getCode().equals(collaborateDTO.getType())){
            HavestDTO havest = havestFeignApi.getHavestById(collaborateDTO.getPid());
            collaborateDTO.setHarvestUserId(havest.getCreateBy());

            harvestUserId = havest.getCreateBy();
        }

        if(CollaborateTypeEnum.REQUIREMENT.getCode().equals(collaborateDTO.getType())){
            Long[] ids = collaborateDTO.getBillboardIds();
            for(int i=0; i<ids.length; i++){
                collaborateDTO.setBillboardId(ids[i]);
                collaborateFeignApi.add(collaborateDTO);
            }
        }else {
            collaborateFeignApi.add(collaborateDTO);
        }

        // 写消息（发起合作：（用户名）向您发起XXX（合作方式）的合作。）
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeEnum.collaborate_mode.name(), collaborateDTO.getMode());
        UserResponse u = this.getUser();
        // 写系统消息
        MessageDTO messageDTO = new MessageDTO();
        // 合作方式
        String modeName = "";
        if(dictionaryDTO != null){
            modeName = dictionaryDTO.getDicValue();
        }
        // 内容
        String content = String.format(MessageLogInfo.billboard_hz, u.getNick(), modeName);
        messageDTO.setContent(content);
        // 榜单发布人
        if(CollaborateTypeEnum.GAIN.getCode().equals(collaborateDTO.getType())){
            // 成果
            messageDTO.setUserId(harvestUserId);
        }else {
            // 帅才ID
            Long talentId = collaborateDTO.getPid();
            TalentPoolDTO talentPool = talentPoolFeignApi.getTalentPoolById(talentId);
            if(talentPool != null){
                UserDTO userDTO = userFeignApi.getUserByMobile(talentPool.getMobie());
                if(userDTO != null){
                    messageDTO.setUserId(userDTO.getId());
                }
            }
        }
        messageDTO.setTitle(content);
        // 发起合作
        messageDTO.setType(3);
        messageDTO.setThirdAvatar(u.getAvatar());
        messageDTO.setThirdName(u.getNick());

        // 榜单ID
        messageDTO.setPid(collaborateDTO.getPid());
        messageFeignApi.add(messageDTO);
    }


    @ApiOperation("邀请揭榜：获取该帅才下的匹配的榜单")
    @GetMapping("/collaborate/getMyBillboradCollaborate")
    List<MyBillboradCollaborateResponse> getMyBillboradCollaborate(@RequestParam("talentId") Long talentId) throws BizException {
        List<MyBillboradCollaborateResponse> responses = billboardTalentRelatedFeignApi.getMyBillboradCollaborate(talentId);
        if(CollectionUtils.isNotEmpty(responses)){
            String techKeys = null;
            for(int i=0; i<responses.size(); i++){
                StringBuffer sb  = new StringBuffer();
                techKeys = responses.get(i).getTechKeys();
                if(StrUtil.isNotBlank(techKeys)){
                    String[] techs = techKeys.split(",");
                    for(int n=0; n<techs.length; n++){
                        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeEnum.tech_domain.name(), techs[n]);
                        if(n != techs.length -1){
                            sb.append(dictionaryDTO.getDicValue()).append(",");
                        }else {
                            sb.append(dictionaryDTO.getDicValue());
                        }
                    }
                }
                responses.get(i).setTechKeys(sb.toString());
            }

        }

        return responses;
    }


    @ApiOperation("获取我的合作")
    @PostMapping("/collaborate/pageQuery")
    PageResult  pageQuery(@RequestBody MyCollaborateRequest request){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        request.setUserId(userId);
        PageResult page  = collaborateFeignApi.pageQuery(request);

        return page;
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
