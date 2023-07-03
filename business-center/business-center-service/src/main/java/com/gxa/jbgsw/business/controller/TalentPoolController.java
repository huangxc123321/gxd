package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.business.client.TalentPoolApi;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolDTO;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolRequest;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolResponse;
import com.gxa.jbgsw.business.service.TalentPoolService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "帅才库管理")
public class TalentPoolController implements TalentPoolApi {
    @Resource
    TalentPoolService talentPoolService;

    @Override
    public void deleteBatchIds(Long[] ids) {
        talentPoolService.deleteBatchIds(ids);
    }

    @Override
    public PageResult<TalentPoolResponse> pageQuery(TalentPoolRequest request) {
        return null;
    }

    @Override
    public void add(TalentPoolDTO talentPoolDTO) {
        talentPoolService.add(talentPoolDTO);
    }

    @Override
    public void update(TalentPoolDTO talentPoolDTO) {
        talentPoolService.updateTalentPool(talentPoolDTO);
    }
}

