package com.gxa.jbgsw.app.controller;

import com.gxa.jbgsw.app.feignapi.AttentionFeignApi;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionResponse;
import com.gxa.jbgsw.common.utils.BaseController;
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
