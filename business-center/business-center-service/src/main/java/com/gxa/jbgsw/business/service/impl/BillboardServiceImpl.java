package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.mapper.BillboardMapper;
import com.gxa.jbgsw.business.protocol.dto.BillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.LastBillboardRequest;
import com.gxa.jbgsw.business.service.BillboardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 榜单信息 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class BillboardServiceImpl extends ServiceImpl<BillboardMapper, Billboard> implements BillboardService {
    @Resource
    BillboardMapper billboardMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        billboardMapper.deleteBatchIds(list);
    }

    @Override
    /**
     * id: 榜单ID
     * isTop: 是否置顶： 0 不置顶 1 置顶
     */
    public void updateTop(Long id, int isTop) {
        LambdaUpdateWrapper<Billboard> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Billboard::getIsTop, isTop)
                           .eq(Billboard::getId, id);

        billboardMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void batchIdsTop(Long[] ids, int isTop) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());

        LambdaUpdateWrapper<Billboard> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Billboard::getIsTop, isTop)
                .in(Billboard::getId, list);

        billboardMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public PageResult<Billboard> pageQuery(BillboardRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<Billboard> responses = billboardMapper.pageQuery(request);
        PageInfo<Billboard> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Billboard>>() {
        }.build(), new TypeBuilder<PageResult<Billboard>>() {}.build());
    }

    @Override
    public void updateSeqNo(Long id, Integer seqNo) {
        LambdaUpdateWrapper<Billboard> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Billboard::getSeqNo, seqNo)
                .eq(Billboard::getId, id);

        billboardMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public PageResult<Billboard> LastBillboardSetData(LastBillboardRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<Billboard> responses = billboardMapper.LastBillboardSetData(request);
        PageInfo<Billboard> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Billboard>>() {
        }.build(), new TypeBuilder<PageResult<Billboard>>() {}.build());
    }
}
