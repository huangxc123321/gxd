package com.gxa.jbgsw.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.basis.entity.Banner;
import com.gxa.jbgsw.basis.entity.WebsiteBottom;
import com.gxa.jbgsw.basis.protocol.dto.BannerRequest;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/5 0005 10:29
 * @Version 2.0
 */
public interface BannerMapper extends BaseMapper<Banner> {
    List<Banner> pageQuery(BannerRequest request);
}
