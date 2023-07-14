package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.TalentPool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.SearchTalentsRequest;
import com.gxa.jbgsw.business.protocol.dto.SearchTalentsResponse;

import java.util.List;

/**
 * <p>
 * 帅才库 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface TalentPoolMapper extends BaseMapper<TalentPool> {

    List<SearchTalentsResponse> queryTalents(SearchTalentsRequest searchTalentsRequest);
}
