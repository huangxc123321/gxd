package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.Collaborate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.HavestCollaborateDTO;
import com.gxa.jbgsw.business.protocol.dto.MyCollaborateHarvestResponse;
import com.gxa.jbgsw.business.protocol.dto.MyCollaborateRequest;
import com.gxa.jbgsw.business.protocol.dto.MyCollaborateTalentResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 我的合作 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface CollaborateMapper extends BaseMapper<Collaborate> {

    List<HavestCollaborateDTO> getHavestCollaborates(@Param("havestId") Long havestId);

    List<MyCollaborateHarvestResponse> getCollaborateHarvest(MyCollaborateRequest request);

    List<MyCollaborateTalentResponse> getCollaborateTalent(MyCollaborateRequest request);
}
