package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.BillboardEconomicRelated;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.AppRequiresAccepptDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardEconomicRelatedResponse;

import java.util.List;

/**
 * <p>
 * 榜单与经纪人的匹配关联表 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
public interface BillboardEconomicRelatedService extends IService<BillboardEconomicRelated> {

    void addEconomicRelated(Long valueOf);

    List<BillboardEconomicRelatedResponse> getEconomicRecommend(Long billboardId);

    BillboardEconomicRelated getMyEconomicMan(Long billboardId);

    void updateRequireStatus(AppRequiresAccepptDTO requiresAccepptDTO);

    void deleteByEconomicId(List<Long> list);
}
