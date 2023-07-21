package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.BillboardHarvestRelated;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.BillboardHarvestRelatedResponse;
import com.gxa.jbgsw.business.protocol.dto.HavestDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 榜单与成果的匹配关联表 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
public interface BillboardHarvestRelatedMapper extends BaseMapper<BillboardHarvestRelated> {

    List<BillboardHarvestRelatedResponse> getHarvestRecommend(@Param("billboardId") Long billboardId);

}
