package com.gxa.jbgsw.basis.controller;

import com.gxa.jbgsw.basis.client.BannerApi;
import com.gxa.jbgsw.basis.entity.Banner;
import com.gxa.jbgsw.basis.feignapi.UserFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.BannerDTO;
import com.gxa.jbgsw.basis.protocol.dto.BannerRequest;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.basis.protocol.enums.BannerStatusEnum;
import com.gxa.jbgsw.basis.protocol.enums.BannerTypeEnum;
import com.gxa.jbgsw.basis.service.BannerService;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Api("广告位管理")
public class BannerController implements BannerApi {
    @Resource
    BannerService bannerService;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    UserFeignApi userFeignApi;

    @Override
    public void deleteBatchIds(Long[] ids) {
        bannerService.deleteBatchIds(ids);
    }

    @Override
    public void add(BannerDTO[] banners) throws BizException {
        bannerService.addBatchs(banners);
    }

    @Override
    public void update(BannerDTO bannerDTO) throws BizException {
        Banner banner = bannerService.getById(bannerDTO.getId());

        BeanUtils.copyProperties(bannerDTO, banner, CopyPropertionIngoreNull.getNullPropertyNames(banner));
        bannerService.saveOrUpdate(banner);
    }

    @Override
    public BannerDTO getBannerById(Long id) throws BizException {
        Banner banner = bannerService.getById(id);
        BannerDTO bannerDTO = mapperFacade.map(banner, BannerDTO.class);
        return bannerDTO;
    }

    @Override
    public void updateSeqNo(Long id, Integer seqNo) {
        bannerService.updateSeqNo(id, seqNo);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        bannerService.updateStatus(id, status);
    }

    @Override
    public PageResult<BannerResponse> pageQuery(BannerRequest request) {
        PageResult<BannerResponse> pages = new PageResult<>();

        PageResult<Banner> pageResult = bannerService.pageQuery(request);
        List<Banner> banners = pageResult.getList();

        List<Long> ids = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(banners)){
            List<BannerResponse> responses = mapperFacade.mapAsList(banners, BannerResponse.class);
            responses.forEach(s->{
                s.setStatusName(BannerStatusEnum.getNameByIndex(s.getStatus()));
            });

            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }

    @Override
    public List<BannerResponse> getIndexBanners(Integer type) {
        List<Banner> banners = bannerService.getIndexBanners(type);
        return mapperFacade.mapAsList(banners, BannerResponse.class);
    }
}
