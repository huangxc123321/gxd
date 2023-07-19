package com.gxa.jbgsw.basis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.entity.Banner;
import com.gxa.jbgsw.basis.mapper.BannerMapper;
import com.gxa.jbgsw.basis.protocol.dto.BannerDTO;
import com.gxa.jbgsw.basis.protocol.dto.BannerRequest;
import com.gxa.jbgsw.basis.protocol.enums.BannerStatusEnum;
import com.gxa.jbgsw.basis.service.BannerService;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl  extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    @Resource
    BannerMapper bannerMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        bannerMapper.deleteBatchIds(list);
    }

    @Override
    public void addBatchs(BannerDTO[] banners) {
        List<BannerDTO> list = Arrays.stream(banners).collect(Collectors.toList());
        List<Banner> bannerList = mapperFacade.mapAsList(list, Banner.class);

        this.saveOrUpdateBatch(bannerList);
    }

    @Override
    public void updateSeqNo(Long id, Integer seqNo) {
        LambdaUpdateWrapper<Banner> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Banner::getSeqNo, seqNo)
                .eq(Banner::getId, id);

        bannerMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        LambdaUpdateWrapper<Banner> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Banner::getStatus, status)
                .eq(Banner::getId, id);

        bannerMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public PageResult<Banner> pageQuery(BannerRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<Banner> banners = bannerMapper.pageQuery(request);
        PageInfo<Banner> pageInfo = new PageInfo<>(banners);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Banner>>() {
        }.build(), new TypeBuilder<PageResult<Banner>>() {}.build());
    }

    @Override
    public List<Banner> getIndexBanners(Integer type) {
        LambdaQueryWrapper<Banner> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 生效
        lambdaQueryWrapper.eq(Banner::getStatus, BannerStatusEnum.EFFECTIVE.getCode());
        if(type != null){
            lambdaQueryWrapper.eq(Banner::getType, type);
        }

        lambdaQueryWrapper.orderByDesc(Banner::getCreateAt);
        lambdaQueryWrapper.last("LIMIT 4");

        List<Banner> banners = bannerMapper.selectList(lambdaQueryWrapper);
        return banners;
    }



}
