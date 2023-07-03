package com.gxa.jbgsw.basis.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.client.DictionaryApi;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryRequest;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryValueQueryRequest;
import com.gxa.jbgsw.basis.service.DictionaryService;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
@Api("字典管理")
public class DictionaryController implements DictionaryApi {
    @Resource
    DictionaryService dictionaryService;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    @ApiOperation(value = "新增/更新字典值", notes = "新增/更新字典值")
    public void saveOrUpdate(DictionaryDTO dictionaryDTO) throws BizException {
        if(dictionaryDTO.getId() == null){
            dictionaryService.insert(dictionaryDTO);
        }else{
            dictionaryService.updateDictionary(dictionaryDTO);
        }
    }

    @Override
    @ApiOperation(value = "获取字典列表", notes = "获取字典列表")
    public PageResult<DictionaryResponse> pageQuery(DictionaryRequest request) {
        return dictionaryService.pageQuery(request);
    }

    @Override
    @ApiOperation(value = "根据ID获取字典值信息", notes = "根据ID获取字典值信息")
    public DictionaryDTO getDictionaryById(Long id) {
        return dictionaryService.getDictionaryById(id);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        dictionaryService.deleteBatchIds(ids);
    }

    @Override
    public DictionaryDTO getDictionaryByCodeAndTypeCode(DictionaryValueQueryRequest request) {
        return dictionaryService.getDictionaryByCodeAndTypeCode(request);
    }

    @Override
    public DictionaryDTO getByCache(String typeCode, String code) {
        DictionaryDTO dictionary = new DictionaryDTO();

        String keys = RedisKeys.DICTIONARY_TYPE_VALUE + typeCode;
        String json = stringRedisTemplate.opsForValue().get(keys);
        JSONArray array = JSONArray.parseArray(json);
        for(int i=0; i<array.size(); i++){
            JSONObject jsonObject = (JSONObject)array.get(i);
            DictionaryResponse response = JSONObject.toJavaObject(jsonObject, DictionaryResponse.class);
            if(response.getDicCode().equals(String.valueOf(code))){
                dictionary.setDicCode(response.getDicCode());
                dictionary.setDicValue(response.getDicValue());
                dictionary.setId(response.getId());
                dictionary.setTypeId(Long.valueOf(response.getTypeId()));

                break;
            }
        }

        // 缓存中没有从字典值，从数据库中获取
        if(dictionary == null){
            DictionaryValueQueryRequest request = new DictionaryValueQueryRequest();
            request.setTypeCode(typeCode);
            request.setCode(code);
            dictionary = this.getDictionaryByCodeAndTypeCode(request);
        }


        return dictionary;
    }
}

