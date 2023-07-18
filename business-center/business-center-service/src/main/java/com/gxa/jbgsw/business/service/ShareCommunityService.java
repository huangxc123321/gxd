package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.ShareCommunity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.ShareCommunityAuditDTO;
import com.gxa.jbgsw.business.protocol.dto.ShareCommunityDTO;
import com.gxa.jbgsw.business.protocol.dto.ShareCommunityRequest;
import com.gxa.jbgsw.common.utils.PageResult;

/**
 * <p>
 * 分享社区 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface ShareCommunityService extends IService<ShareCommunity> {

    void updateStatus(ShareCommunityAuditDTO shareCommunityAuditDTO);

    void deleteBatchIds(Long[] ids);

    ShareCommunityDTO detail(Long id);

    PageResult<ShareCommunity> pageQuery(ShareCommunityRequest request);
}
