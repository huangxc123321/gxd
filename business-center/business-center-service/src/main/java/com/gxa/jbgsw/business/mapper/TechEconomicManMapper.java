package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.TechEconomicMan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.*;

import java.util.List;

/**
 * <p>
 * 技术经济人 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface TechEconomicManMapper extends BaseMapper<TechEconomicMan> {

    List<TechEconomicMan> pageQuery(TechEconomicManRequest request);

    List<SearchEconomicMansResponse> queryEconomicMans(SearchEconomicMansRequest searchTalentsRequest);

    List<String> getLabels();

    List<TechEconomicManRequiresResponse> getEconomicManRequires(TechEconomicManRequiresRequest request);

    long getOrders(TechEconomicManRequiresRequest request);
}
