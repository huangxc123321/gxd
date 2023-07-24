package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.BillboardTalentRelated;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.BillboardTalentRelatedResponse;
import com.gxa.jbgsw.business.protocol.dto.HarvestBillboardRelatedDTO;
import com.gxa.jbgsw.business.protocol.dto.HavestCollaborateDTO;

import java.util.List;

/**
 * <p>
 * 榜单与帅才的匹配关联表 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
public interface BillboardTalentRelatedService extends IService<BillboardTalentRelated> {

    void addTalentRelated(Long valueOf);

    List<BillboardTalentRelatedResponse> getTalentRecommend(Long billboardId);

    List<HarvestBillboardRelatedDTO> getBillboardRecommendByTalentId(Long id);

    List<HavestCollaborateDTO> getCollaborateByTalentId(Long id);
}
