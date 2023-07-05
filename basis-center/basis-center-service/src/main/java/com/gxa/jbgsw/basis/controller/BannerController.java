package com.gxa.jbgsw.basis.controller;

import com.gxa.jbgsw.basis.client.BannerApi;
import com.gxa.jbgsw.basis.entity.Banner;
import com.gxa.jbgsw.basis.protocol.dto.BannerDTO;
import com.gxa.jbgsw.basis.protocol.dto.BannerRequest;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.basis.service.BannerService;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api("广告位管理")
public class BannerController implements BannerApi {
    @Resource
    BannerService bannerService;
    @Resource
    MapperFacade mapperFacade;

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
        List<Banner> harvests = pageResult.getList();
        if(CollectionUtils.isNotEmpty(harvests)){
            List<BannerResponse> responses = mapperFacade.mapAsList(harvests, BannerResponse.class);

            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }
}
