package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.admin.feignapi.DictionaryTypeFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "字典管理")
@RestController
@Slf4j
public class DictionaryController extends BaseController {
    @Autowired
    DictionaryTypeFeignApi dictionaryTypeFeignApi;
    @Autowired
    DictionaryFeignApi dictionaryFeignApi;


    @ApiOperation("获取分类下的所有字典值")
    @PostMapping("/dictionary/getDictionaryByCode")
    public List<DictionaryResponse> getDictionaryByCode(@RequestBody DictionaryCodeRequest request){
        return dictionaryTypeFeignApi.getDictionaryByCode(request);
    }

    @ApiOperation("获取字典分类")
    @PostMapping("/dictionary/getAllDictionaryType")
    public List<DictionaryTypeResponse> list(@RequestBody DictionaryTypeRequest request){
        return dictionaryTypeFeignApi.list(request);
    }

    @ApiOperation("新增或修改字典值")
    @PostMapping("/dictionary/saveOrUpdate")
    void saveOrUpdate(@RequestBody DictionaryDTO dictionaryDTO) throws BizException {
        dictionaryFeignApi.saveOrUpdate(dictionaryDTO);
    }

    @ApiOperation("获取字典列表")
    @PostMapping("/dictionary/pageQuery")
    public PageResult<DictionaryResponse> pageQuery(@RequestBody DictionaryRequest request){
        return dictionaryFeignApi.pageQuery(request);
    }

    @ApiOperation(value = "批量删除字典值", notes = "批量删除字典值")
    @PostMapping("/dictionary/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        dictionaryFeignApi.deleteBatchIds(ids);
    }


}
