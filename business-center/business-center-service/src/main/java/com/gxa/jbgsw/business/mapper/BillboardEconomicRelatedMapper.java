package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.Attention;
import com.gxa.jbgsw.business.entity.BillboardEconomicRelated;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.BillboardEconomicRelatedResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 榜单与经纪人的匹配关联表 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
public interface BillboardEconomicRelatedMapper extends BaseMapper<BillboardEconomicRelated> {
    List<BillboardEconomicRelatedResponse> getEconomicRecommend(@Param("billboardId") Long billboardId);

    List<BillboardEconomicRelatedResponse> getAdminEconomicRecommend(@Param("billboardId") Long billboardId);
}
