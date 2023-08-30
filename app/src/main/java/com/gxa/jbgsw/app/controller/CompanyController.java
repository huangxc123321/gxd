package com.gxa.jbgsw.app.controller;

import com.gxa.jbgsw.business.protocol.dto.AttentionDTO;
import com.gxa.jbgsw.business.protocol.dto.CompanyPCResponse;
import com.gxa.jbgsw.business.protocol.enums.AttentionStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.AttentionTypeEnum;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.app.feignapi.AttentionFeignApi;
import com.gxa.jbgsw.app.feignapi.CompanyFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "企业管理")
@RestController
@Slf4j
@ResponseBody
public class CompanyController extends BaseController {
    @Resource
    CompanyFeignApi companyFeignApi;
    @Resource
    AttentionFeignApi attentionFeignApi;

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "企业ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/company/getCompanyById4Pc")
    public CompanyPCResponse getCompanyById(@RequestParam("id")Long id){
        CompanyPCResponse  companyPCResponse = companyFeignApi.getCompanyById4Pc(id);

        // 判断是关注
        Long userId = this.getUserId();
        if(userId != null){
            AttentionDTO attentionDTO = null;
            // 关注： 帅才
            Integer attentionType = AttentionTypeEnum.TALENT.getCode();

            AttentionDTO atInfo = attentionFeignApi.getAttention(id, userId, attentionType);
            if(atInfo != null){
                companyPCResponse.setAttentionStatus(AttentionStatusEnum.ATTENTION.getCode());
            }
        }

        return companyPCResponse;
    }


    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "企业名称", name = "name", dataType = "String", paramType = "query"),
    })
    @GetMapping("/company/getCompanyByName")
    public CompanyPCResponse getCompanyByName(@RequestParam("name") String name){
        CompanyPCResponse  companyPCResponse = companyFeignApi.getCompanyByName(name);
        if(companyPCResponse == null || companyPCResponse.getId() == null){
            throw new BizException(BusinessErrorCode.BUZ_IS_NOT_EXIST);
        }

        // 判断是关注
        Long userId = this.getUserId();
        if(userId != null  && companyPCResponse != null &&  companyPCResponse.getId() != null){
            AttentionDTO attentionDTO = null;
            // 关注： 帅才
            Integer attentionType = AttentionTypeEnum.BUZ.getCode();

            AttentionDTO atInfo = attentionFeignApi.getAttention(companyPCResponse.getId(), userId, attentionType);
            if(atInfo != null){
                companyPCResponse.setAttentionStatus(AttentionStatusEnum.ATTENTION.getCode());
            }
        }

        return companyPCResponse;
    }



}
