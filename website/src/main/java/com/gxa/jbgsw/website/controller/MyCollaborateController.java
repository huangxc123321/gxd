package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.CollaborateDTO;
import com.gxa.jbgsw.business.protocol.dto.HavestDTO;
import com.gxa.jbgsw.business.protocol.dto.MyBillboradCollaborateResponse;
import com.gxa.jbgsw.business.protocol.enums.CollaborateTypeEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.BillboardTalentRelatedFeignApi;
import com.gxa.jbgsw.website.feignapi.CollaborateFeignApi;
import com.gxa.jbgsw.website.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.website.feignapi.HavestFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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


    @ApiOperation("新增合作")
    @PostMapping("/collaborate/add")
    void add(@RequestBody CollaborateDTO collaborateDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        collaborateDTO.setEffectAt(new Date());
        collaborateDTO.setLaunchUserId(userId);
        collaborateDTO.setLaunchUserName(this.getUserNick());
        // 合作类型：0 成果合作  1 需求合作
        if(CollaborateTypeEnum.GAIN.getCode().equals(collaborateDTO.getType())){
            //HavestDTO havest = havestFeignApi.getHavestById(collaborateDTO.getPid());
            //collaborateDTO.setHarvestUserId(havest.getId());
        }

        collaborateFeignApi.add(collaborateDTO);
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








}
