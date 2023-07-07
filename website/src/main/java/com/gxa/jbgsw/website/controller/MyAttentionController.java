package com.gxa.jbgsw.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.website.feignapi.AttentionFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        myAttentionRequest.setCreateBy(this.getUserId());
        MyAttentionResponse response = attentionFeignApi.queryMyAttentions(myAttentionRequest);

        return response;
    }


}
