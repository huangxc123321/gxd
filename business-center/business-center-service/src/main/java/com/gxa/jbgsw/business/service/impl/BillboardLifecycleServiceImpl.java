package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.entity.Billboardlifecycle;
import com.gxa.jbgsw.business.mapper.BillboardlifecycleMapper;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleResponse;
import com.gxa.jbgsw.business.service.BillboardLifecycleService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BillboardLifecycleServiceImpl extends ServiceImpl<BillboardlifecycleMapper, Billboardlifecycle> implements BillboardLifecycleService {
    @Resource
    BillboardlifecycleMapper billboardlifecycleMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<BillboardlifecycleResponse> selectList(Long pid) {
        LambdaQueryWrapper<Billboardlifecycle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Billboardlifecycle::getPid, pid);
        lambdaQueryWrapper.orderByDesc(Billboardlifecycle::getCreateAt);
        List<Billboardlifecycle> billboardlifecycles = billboardlifecycleMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(billboardlifecycles)){
            return mapperFacade.mapAsList(billboardlifecycles, BillboardlifecycleResponse.class);
        }

        return null;
    }
}
