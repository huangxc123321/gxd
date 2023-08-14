package com.gxa.jbgsw.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AttentionStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.CollectionStatusEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.AttentionFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "用户中心: 我的关注")
@RestController
@Slf4j
@ResponseBody
public class MyAttentionController extends BaseController {
    @Resource
    AttentionFeignApi attentionFeignApi;

    @ApiOperation("获取我的关注")
    @PostMapping("/attention/pageQuery")
    MyAttentionResponse pageQuery(@RequestBody MyAttentionRequest myAttentionRequest){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        myAttentionRequest.setCreateBy(userId);
        MyAttentionResponse response = attentionFeignApi.queryMyAttentions(myAttentionRequest);

        return response;
    }


    @ApiOperation("关注/取消关注")
    @PostMapping("/attention/add")
    void addAttention(@RequestBody AttentionDTO attentionDTO){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        attentionDTO.setCreateAt(new Date());
        attentionDTO.setUserId(this.getUserId());

        if(AttentionStatusEnum.ATTENTION.getCode().equals(attentionDTO.getStatus())){
            attentionFeignApi.add(attentionDTO);
        }else {
            attentionFeignApi.delete(attentionDTO);
        }
    }




}
