package com.gxa.jbgsw.basis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.entity.Banner;
import com.gxa.jbgsw.basis.entity.LeaveWords;
import com.gxa.jbgsw.basis.mapper.LeaveWordsMapper;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsRequest;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsResponse;
import com.gxa.jbgsw.basis.service.LeaveWordsService;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveWordsServiceImpl  extends ServiceImpl<LeaveWordsMapper, LeaveWords> implements LeaveWordsService {
    @Resource
    LeaveWordsMapper leaveWordsMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        leaveWordsMapper.deleteBatchIds(list);
    }

    @Override
    public PageResult<LeaveWordsResponse> pageQuery(LeaveWordsRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<LeaveWords> leaveWords = leaveWordsMapper.pageQuery(request);
        if(CollectionUtils.isNotEmpty(leaveWords)){
            List<LeaveWordsResponse> responses = mapperFacade.mapAsList(leaveWords, LeaveWordsResponse.class);

            PageInfo<LeaveWordsResponse> pageInfo = new PageInfo<>(responses);
            //类型转换
            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<LeaveWordsResponse>>() {
            }.build(), new TypeBuilder<PageResult<LeaveWordsResponse>>() {}.build());
        }

        return new PageResult<>();
    }
}
