package com.gxa.jbgsw.basis.controller;


import com.gxa.jbgsw.basis.client.DictionaryTypeApi;
import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.basis.service.DictionaryTypeService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@RestController
@Slf4j
@Api("字典类型管理")
public class DictionaryTypeController implements DictionaryTypeApi {
    @Autowired
    DictionaryTypeService dictionaryTypeService;

    @Override
    @ApiOperation(value = "获取字典类型分页数据", notes = "获取字典类型分页数据")
    public PageResult<DictionaryTypeResponse> pageQuery(DictionaryTypePageRequest request) {
        return dictionaryTypeService.pageQuery(request);
    }

    @Override
    @ApiOperation(value = "获取字典类型列表数据", notes = "获取字典类型列表数据")
    public List<DictionaryTypeResponse> list(DictionaryTypeRequest request) {
        return dictionaryTypeService.list(request);
    }

    @Override
    @ApiOperation(value = "获取字典类型下的所有字典值", notes = "获取字典类型下的所有字典值")
    public List<DictionaryResponse> getDictionaryByCode(DictionaryCodeRequest request) {
        return dictionaryTypeService.getDictionaryByCode(request);
    }

    @Override
    public List<DictionaryResponse> getByCode(String code) {
        return dictionaryTypeService.getByCode(code);
    }
}

