package com.gxa.jbgsw.basis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.basis.entity.Banner;
import com.gxa.jbgsw.basis.entity.WebsiteBottom;
import com.gxa.jbgsw.basis.protocol.dto.BannerDTO;
import com.gxa.jbgsw.basis.protocol.dto.BannerRequest;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/5 0005 10:27
 * @Version 2.0
 */
public interface BannerService extends IService<Banner> {
    void deleteBatchIds(Long[] ids);

    void addBatchs(BannerDTO[] banners);

    void updateSeqNo(Long id, Integer seqNo);

    void updateStatus(Long id, Integer status);

    PageResult<Banner> pageQuery(BannerRequest request);

    List<Banner> getIndexBanners(Integer type);
}
