package com.gxa.jbgsw.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.business.protocol.dto.CompanyPCResponse;
import com.gxa.jbgsw.website.feignapi.CompanyFeignApi;
import com.gxa.jbgsw.business.protocol.dto.CompanyDTO;
import com.gxa.jbgsw.business.protocol.dto.CompanyRequest;
import com.gxa.jbgsw.business.protocol.dto.CompanyResponse;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "企业管理")
@RestController
@Slf4j
@ResponseBody
public class CompanyController extends BaseController {
    @Resource
    CompanyFeignApi companyFeignApi;

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "企业ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/company/getCompanyById4Pc")
    public CompanyPCResponse getCompanyById(@RequestParam("id")Long id){
        return companyFeignApi.getCompanyById4Pc(id);
    }


}
