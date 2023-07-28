package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.AttentionApi;
import com.gxa.jbgsw.business.entity.Attention;
import com.gxa.jbgsw.business.protocol.dto.AttentionDTO;
import com.gxa.jbgsw.business.protocol.dto.AttentionDynamicDTO;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionResponse;
import com.gxa.jbgsw.business.service.AttentionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "关注管理")
public class AttentionController implements AttentionApi {
    @Resource
    AttentionService attentionService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public MyAttentionResponse queryMyAttentions(MyAttentionRequest myAttentionRequest) {
        MyAttentionResponse response = attentionService.queryMyAttentions(myAttentionRequest);

        return response;
    }

    @Override
    public void add(AttentionDTO attentionDTO) {
        Attention attention = mapperFacade.map(attentionDTO, Attention.class);

        attentionService.save(attention);
    }

    @Override
    public void delete(AttentionDTO attentionDTO) {
        attentionService.deleteAttention(attentionDTO.getPid(), attentionDTO.getUserId(), attentionDTO.getType());
    }

    @Override
    public AttentionDTO getAttention(Long pid, Long userId, Integer attentionType) {
        return attentionService.getAttention(pid, userId, attentionType);
    }

    @Override
    public Integer getAttentionNum(Long userId) {
        return attentionService.getAttentionNum(userId);
    }

    @Override
    public List<AttentionDynamicDTO> getDynamicInfo(Long userId) {
        return attentionService.getDynamicInfo(userId);
    }
}

