package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.Attention;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionInfo;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;

import java.util.List;

/**
 * <p>
 * 我的关注 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface AttentionMapper extends BaseMapper<Attention> {

    List<MyAttentionInfo> getMyAttentionInfos(MyAttentionRequest myAttentionRequest);
}
