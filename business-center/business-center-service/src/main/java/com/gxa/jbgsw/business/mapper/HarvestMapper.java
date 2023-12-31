package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.Harvest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.HarvestRequest;
import com.gxa.jbgsw.business.protocol.dto.HarvestResponse;
import com.gxa.jbgsw.business.protocol.dto.SearchHarvestsRequest;
import com.gxa.jbgsw.business.protocol.dto.SearchHavestResponse;

import java.util.List;

/**
 * <p>
 * 成果信息 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface HarvestMapper extends BaseMapper<Harvest> {

    List<HarvestResponse> pageQuery(HarvestRequest request);

    List<SearchHavestResponse> queryHarvests(SearchHarvestsRequest searchHarvestsRequest);

    List<Harvest> pageMyHarvestQuery(HarvestRequest request);
}
