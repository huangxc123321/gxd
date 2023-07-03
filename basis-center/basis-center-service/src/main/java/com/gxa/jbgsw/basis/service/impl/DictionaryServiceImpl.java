package com.gxa.jbgsw.basis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.entity.Dictionary;
import com.gxa.jbgsw.basis.entity.DictionaryType;
import com.gxa.jbgsw.basis.mapper.DictionaryMapper;
import com.gxa.jbgsw.basis.mapper.DictionaryTypeMapper;
import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.basis.service.DictionaryService;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {
    @Resource
    DictionaryMapper dictionaryMapper;
    @Resource
    DictionaryTypeMapper dictionaryTypeMapper;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void insert(DictionaryDTO dictionaryDTO) {
        Dictionary dictionary = mapperFacade.map(dictionaryDTO, Dictionary.class);
        dictionary.setCreateAt(new Date());
        dictionary.setCreateBy(dictionaryDTO.getUserId());
        // 主键按策略自动生成
        dictionary.setId(null);

        dictionaryMapper.insert(dictionary);
        // 重新初始化redis数据
        initDictionary();
    }

    @Override
    public void updateDictionary(DictionaryDTO dictionaryDTO) {
        Dictionary dictionary = mapperFacade.map(dictionaryDTO, Dictionary.class);
        dictionary.setUpdateAt (new Date());
        dictionary.setUpdateBy(dictionaryDTO.getUserId());

        dictionaryMapper.updateById(dictionary);

        // 重新初始化redis数据
        initDictionary();
    }

    @Override
    public PageResult<DictionaryResponse> pageQuery(DictionaryRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(request.getTypeId() != null){
            lambdaQueryWrapper.eq(Dictionary::getTypeId, request.getTypeId());
        }
        // 排序，按排序showInx来
        lambdaQueryWrapper.orderByAsc(Dictionary::getShowInx);
        List<Dictionary> dictionaries = dictionaryMapper.selectList(lambdaQueryWrapper);

        PageInfo<Dictionary> pageInfo = new PageInfo<>(dictionaries);
        // 返回查询对象
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Dictionary>>() {
        }.build(), new TypeBuilder<PageResult<DictionaryResponse>>() {}.build());
    }

    @Override
    public DictionaryDTO getDictionaryById(Long id) {
        Dictionary dictionary = dictionaryMapper.selectById(id);
        DictionaryDTO dictionaryDTO = mapperFacade.map(dictionary, DictionaryDTO.class);
        return dictionaryDTO;
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        dictionaryMapper.deleteBatchIds(list);

        // 重新初始化redis数据
        initDictionary();
    }

    @Override
    public DictionaryDTO getDictionaryByCodeAndTypeCode(DictionaryValueQueryRequest request) {
        return dictionaryMapper.getDictionaryByCodeAndTypeCode(request);
    }

    @Override
    public void initDictionary() {
        // 删除之前的
        String keys = RedisKeys.DICTIONARY_TYPE_VALUE + "*";
        stringRedisTemplate.delete(keys);

        // 重新赋值
        DictionaryTypeRequest request = new DictionaryTypeRequest();
        List<DictionaryTypeResponse> responses = list(request);
        responses.stream().forEach(s->{
            Map<String, Object> map = new HashMap<>();
            String code = s.getCode();
            List<DictionaryResponse> dictionaryResponses = getByCode(code);
            map.put(code, JSONObject.toJSONString(dictionaryResponses));
            String key = RedisKeys.DICTIONARY_TYPE_VALUE+code;
            stringRedisTemplate.opsForValue().set(key,JSONObject.toJSONString(dictionaryResponses));
        });
    }

    @Override
    public List<DictionaryTypeResponse> list(DictionaryTypeRequest request) {
        // 根据条件查询
        LambdaQueryWrapper<DictionaryType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(request.getCode())){
            lambdaQueryWrapper.like(DictionaryType::getCode, request.getCode());
        }

        List<DictionaryType> dictionaryTypes = dictionaryTypeMapper.selectList(lambdaQueryWrapper);
        // 转换对象
        List<DictionaryTypeResponse> dictionaryTypeResponses = mapperFacade.mapAsList(dictionaryTypes, DictionaryTypeResponse.class);

        return dictionaryTypeResponses;
    }

    @Override
    public List<DictionaryResponse> getByCode(String code) {
        LambdaQueryWrapper<DictionaryType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictionaryType::getCode, code);
        lambdaQueryWrapper.orderByDesc(DictionaryType::getId);
        lambdaQueryWrapper.last("limit 1");
        DictionaryType dictionaryType = dictionaryTypeMapper.selectOne(lambdaQueryWrapper);
        if(dictionaryType != null){
            Long typeId = dictionaryType.getId();
            LambdaQueryWrapper<Dictionary> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Dictionary::getTypeId, typeId);
            wrapper.orderByAsc(Dictionary::getShowInx);
            List<Dictionary> list = dictionaryMapper.selectList(wrapper);

            List<DictionaryResponse> responses = mapperFacade.mapAsList(list, DictionaryResponse.class);
            return responses;
        }

        return null;
    }

}
