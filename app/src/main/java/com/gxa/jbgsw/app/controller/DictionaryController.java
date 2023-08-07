package com.gxa.jbgsw.app.controller;

import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.app.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.app.feignapi.DictionaryTypeFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiOperation("根据字典类型获取")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "字典类型", name = "typeCode", dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "字典值", name = "code", dataType = "String", paramType = "query")
    })
    @GetMapping("/dictionary/getByCache") public DictionaryDTO getByCache(@RequestParam("typeCode") String typeCode, @RequestParam("code") String code){
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(typeCode, code);
        return dictionaryDTO;
    }


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
