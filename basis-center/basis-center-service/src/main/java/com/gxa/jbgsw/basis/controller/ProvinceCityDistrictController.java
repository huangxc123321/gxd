package com.gxa.jbgsw.basis.controller;


import com.gxa.jbgsw.basis.client.ProvinceCityDistrictApi;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryRequest;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryResponse;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictVO;
import com.gxa.jbgsw.basis.service.ProvinceCityDistrictService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 省市县数据表 前端控制器
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@RestController
@Slf4j
@Api("省市县数据管理")
public class ProvinceCityDistrictController implements ProvinceCityDistrictApi {
    @Autowired
    ProvinceCityDistrictService provinceCityDistrictService;

    @Override
    @ApiOperation(value = "根据查询条件获取地区信息", notes = "根据查询条件获取地区信息")
    public PageResult<ProvinceCityDistrictQueryResponse> list(@RequestBody ProvinceCityDistrictQueryRequest request) {
        return provinceCityDistrictService.pageQuery(request);
    }

    @Override
    @ApiOperation(value = "获取子下所有地区信息", notes = "获取子下所有地区信息")
    public List<ProvinceCityDistrictQueryResponse> sonlist(@RequestParam String pid) {
        return provinceCityDistrictService.pageSonQuery(pid);
    }

    @Override
    @ApiOperation(value = "获取上一级地址", notes = "获取上一级地址")
    public ProvinceCityDistrictVO getProvinceCityDistrictById(Integer id) {
        return provinceCityDistrictService.getProvinceCityDistrictById(id);
    }

    @Override
    @ApiOperation(value = "通过名称获取上一级地址", notes = "通过名称获取上一级地址")
    public ProvinceCityDistrictVO getProvinceCityDistrictByName(String name) {
        return provinceCityDistrictService.getProvinceCityDistrictByName(name);
    }
}

