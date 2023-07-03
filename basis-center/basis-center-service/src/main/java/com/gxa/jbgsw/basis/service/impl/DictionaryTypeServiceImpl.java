package com.gxa.jbgsw.basis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.entity.Dictionary;
import com.gxa.jbgsw.basis.entity.DictionaryType;
import com.gxa.jbgsw.basis.mapper.DictionaryMapper;
import com.gxa.jbgsw.basis.mapper.DictionaryTypeMapper;
import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.basis.service.DictionaryTypeService;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@Service
public class DictionaryTypeServiceImpl extends ServiceImpl<DictionaryTypeMapper, DictionaryType> implements DictionaryTypeService {
    @Resource
    DictionaryTypeMapper dictionaryTypeMapper;
    @Resource
    DictionaryMapper dictionaryMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public PageResult<DictionaryTypeResponse> pageQuery(DictionaryTypePageRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        // 根据条件查询
        LambdaQueryWrapper<DictionaryType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(request.getCode())){
            lambdaQueryWrapper.like(DictionaryType::getCode, request.getCode());
        }
        List<DictionaryType> dictionaryTypes = dictionaryTypeMapper.selectList(lambdaQueryWrapper);
        PageInfo<DictionaryType> pageInfos = new PageInfo<>(dictionaryTypes);

        // 返回查询对象
        return mapperFacade.map(pageInfos, new TypeBuilder<PageInfo<DictionaryType>>() {
        }.build(), new TypeBuilder<PageResult<DictionaryTypeResponse>>() {}.build());
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
    public List<DictionaryResponse> getDictionaryByCode(DictionaryCodeRequest request) {
        LambdaQueryWrapper<DictionaryType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictionaryType::getCode, request.getCode());
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
