package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.Collaborate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.HavestCollaborateDTO;
import com.gxa.jbgsw.business.protocol.dto.MyCollaborateRequest;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * <p>
 * 我的合作 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface CollaborateService extends IService<Collaborate> {

    List<HavestCollaborateDTO> getHavestCollaborates(Long id);

    void saveCollaborate(Collaborate collaborate);

    void delete(Long id);

    PageResult getCollaborateHarvest(MyCollaborateRequest request);

    PageResult getCollaborateTalent(MyCollaborateRequest request);

    Collaborate getCollaborateInfo(Long userId, Long id);
}
