package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.TalentPoolFeignApi;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolDTO;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "帅才库管理")
@RestController
@Slf4j
@ResponseBody
public class TalentPoolFontController extends BaseController {
    @Resource
    TalentPoolFeignApi talentPoolFeignApi;


    @ApiOperation(value = "批量删除帅才", notes = "批量删除帅才")
    @PostMapping("/business/talent/pool/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        talentPoolFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("新增帅才信息")
    @PostMapping("/business/talent/pool/add")
    void add(@RequestBody TalentPoolDTO talentPoolDTO) throws BizException {
        talentPoolDTO.setCreateBy(this.getUserId());

        talentPoolFeignApi.add(talentPoolDTO);
    }

    @ApiOperation("编辑帅才信息")
    @PostMapping("/business/talent/pool/update")
    void update(@RequestBody TalentPoolDTO talentPoolDTO) throws BizException {
        if(talentPoolDTO == null || talentPoolDTO.getId() == null){
            throw new BizException(BusinessErrorCode.BUSINESS_PARAMS_ERROR);
        }
        talentPoolDTO.setUpdateBy(this.getUserId());

        talentPoolFeignApi.update(talentPoolDTO);
    }

    @ApiOperation("查看帅才信息")
    @PostMapping("/business/talent/pool/detail")
    void detail(@RequestParam("id") Long id) throws BizException {
        // talentPoolFeignApi.detail(id);
    }



}
