package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.AttentionApi;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionResponse;
import com.gxa.jbgsw.business.service.AttentionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(tags = "关注管理")
public class AttentionController implements AttentionApi {
    @Resource
    AttentionService attentionService;

    @Override
    public MyAttentionResponse queryMyAttentions(MyAttentionRequest myAttentionRequest) {
        MyAttentionResponse response = attentionService.queryMyAttentions(myAttentionRequest);

        return response;
    }
}

