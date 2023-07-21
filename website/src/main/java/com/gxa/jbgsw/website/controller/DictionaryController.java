package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.website.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.website.feignapi.DictionaryTypeFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        List<DictionaryResponse> dictionaryResponses = dictionaryTypeFeignApi.getDictionaryByCode(request);
        System.out.println("获取分类下的所有字典值: "+dictionaryResponses.size());
        return dictionaryResponses;
    }

    @ApiOperation("获取字典分类")
    @PostMapping("/dictionary/getAllDictionaryType")
    public List<DictionaryTypeResponse> getAllDictionaryType(@RequestBody DictionaryTypeRequest request){
        List<DictionaryTypeResponse> dictionaryTypeResponses = dictionaryTypeFeignApi.list(request);
        dictionaryTypeResponses.stream().forEach(s->{
            System.out.println(s.getCode()+"  -- "+s.getName()+" -- "+s.getId());
        });
        System.out.println("获取字典分类: "+dictionaryTypeResponses.size());
        return dictionaryTypeResponses;
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
