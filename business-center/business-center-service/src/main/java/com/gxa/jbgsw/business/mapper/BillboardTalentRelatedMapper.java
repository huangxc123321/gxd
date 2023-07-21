package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.BillboardTalentRelated;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.BillboardTalentRelatedResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 榜单与帅才的匹配关联表 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
public interface BillboardTalentRelatedMapper extends BaseMapper<BillboardTalentRelated> {

    List<BillboardTalentRelatedResponse> getTalentRecommend(@Param("billboardId") Long billboardId);
}
