package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.ShareCommunity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.MyShareCommunityRequest;
import com.gxa.jbgsw.business.protocol.dto.ShareCommunityRequest;
import com.gxa.jbgsw.business.protocol.dto.ShareCommuntiyRequest;

import java.util.List;

/**
 * <p>
 * 分享社区 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface ShareCommunityMapper extends BaseMapper<ShareCommunity> {

    List<ShareCommunity> pageQuery(ShareCommunityRequest request);

    List<ShareCommunity> getMyShareCommunityPages(MyShareCommunityRequest request);

    List<ShareCommunity> getShareItems(ShareCommuntiyRequest request);
}
