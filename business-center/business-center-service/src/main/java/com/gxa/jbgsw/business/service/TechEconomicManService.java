package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.TechEconomicMan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.SearchEconomicMansRequest;
import com.gxa.jbgsw.business.protocol.dto.SearchEconomicMansResponse;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManRequest;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * <p>
 * 技术经济人 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface TechEconomicManService extends IService<TechEconomicMan> {

    void deleteBatchIds(Long[] ids);

    PageResult<TechEconomicMan> pageQuery(TechEconomicManRequest request);

    PageResult<SearchEconomicMansResponse> queryEconomicMans(SearchEconomicMansRequest searchTalentsRequest);

    List<String> getLabels();
}
