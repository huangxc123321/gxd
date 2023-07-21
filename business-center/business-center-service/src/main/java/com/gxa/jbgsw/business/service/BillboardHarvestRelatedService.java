package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.BillboardHarvestRelated;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.BillboardHarvestRelatedResponse;
import com.gxa.jbgsw.business.protocol.dto.HavestDTO;

import java.util.List;

/**
 * <p>
 * 榜单与成果的匹配关联表 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
public interface BillboardHarvestRelatedService extends IService<BillboardHarvestRelated> {

    void addHarvestRelated(Long billboardId);

    List<BillboardHarvestRelatedResponse> getHarvestRecommend(Long billboardId);
}
