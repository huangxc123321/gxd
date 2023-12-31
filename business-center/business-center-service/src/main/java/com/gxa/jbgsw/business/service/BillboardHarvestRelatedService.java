package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.BillboardHarvestRelated;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.*;

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

    List<HavestCollaborateDTO> getHarvestRecommendByHarvestId(Long harvestId);

    List<BillboardResponse> getHarvestByHarvestId(Long harvestId);

    List<BillboardHarvestRelatedResponse> getBillboardstByHarvestId(Long harvestId);

    List<RelateHavestDTO> getRelatedHavestByBillboardId(Long id);

    void deleteByHarvestId(List<Long> list);

    void addBillboardRelated(Long valueOf);

    void deleteByBillboardId(List<Long> list);
}
