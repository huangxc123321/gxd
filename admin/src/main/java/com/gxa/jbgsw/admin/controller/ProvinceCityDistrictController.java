package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.ProvinceCityDistrictFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryRequest;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryResponse;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictVO;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "省市区地址管理")
@RestController
@Slf4j
public class ProvinceCityDistrictController extends BaseController {
    @Resource
    ProvinceCityDistrictFeignApi provinceCityDistrictFeignApi;

    @PostMapping("/address/list")
    @ApiOperation(value = "根据查询条件获取地区信息", notes = "根据查询条件获取地区信息")
    public PageResult<ProvinceCityDistrictQueryResponse> list(@RequestBody ProvinceCityDistrictQueryRequest request){
        return provinceCityDistrictFeignApi.list(request);
    }

    @GetMapping("/address/sonlist")
    @ApiOperation(value = "获取子下所有地区信息", notes = "获取子下所有地区信息")
    public List<ProvinceCityDistrictQueryResponse> sonlist(@RequestParam("pid") String pid){
        return provinceCityDistrictFeignApi.sonlist(pid);
    }

    @GetMapping("/address/getProvinceCityDistrictById")
    @ApiOperation(value = "获取上一级地址", notes = "获取上一级地址")
    public ProvinceCityDistrictVO getProvinceCityDistrictById(@RequestParam("id") Integer id){
        return provinceCityDistrictFeignApi.getProvinceCityDistrictById(id);
    }

    @GetMapping("/address/getProvinceCityDistrictByName")
    @ApiOperation(value = "通过名称获取上一级地址", notes = "通过名称获取上一级地址")
    public ProvinceCityDistrictVO getProvinceCityDistrictByName(@RequestParam("name") String name){
        return provinceCityDistrictFeignApi.getProvinceCityDistrictByName(name);
    }

}
