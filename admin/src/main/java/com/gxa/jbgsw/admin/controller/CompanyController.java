package com.gxa.jbgsw.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.CompanyFeignApi;
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
    @GetMapping("/company/getCompanyById")
    public CompanyDTO getCompanyById(@RequestParam("id")Long id){
        return companyFeignApi.getCompanyById(id);
    }


    @ApiOperation("新增企业信息")
    @PostMapping("/company/add")
    void add(@RequestBody CompanyDTO companyDTO) throws BizException {
        companyDTO.setCreateBy(this.getUserId());
        // 设置默认的待揭榜
        companyDTO.setStatus(0);
        companyFeignApi.add(companyDTO);
    }

    @ApiOperation(value = "批量删除企业", notes = "批量删除企业")
    @PostMapping("/company/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        companyFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("修改企业信息")
    @PostMapping("/company/update")
    void update(@RequestBody CompanyDTO companyDTO) throws BizException {
        companyDTO.setUpdateBy(this.getUserId());
        companyDTO.setUpdateAt(new Date());

        companyFeignApi.update(companyDTO);
    }

    @ApiOperation("获取企业列表")
    @PostMapping("/company/pageQuery")
    PageResult<CompanyResponse> pageQuery(@RequestBody CompanyRequest request){
        PageResult<CompanyResponse> pageResult = companyFeignApi.pageQuery(request);
        log.info("Result：{}", JSONObject.toJSONString(pageResult));

        return pageResult;
    }


}
