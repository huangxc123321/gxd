package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.Harvest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * <p>
 * 成果信息 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface HarvestService extends IService<Harvest> {

    List<Harvest> getHarvesByHolder(String holder);

    void deleteBatchIds(Long[] ids);

    PageResult<HarvestResponse> pageQuery(HarvestRequest request);

    PageResult<SearchHavestResponse> queryHarvests(SearchHarvestsRequest searchHarvestsRequest);

    List<Harvest> getHarvesByTechDomain(String techDomain);

    List<RecommendHavestResponse> getRecommendHavest();

    PageResult<Harvest> pageMyHarvestQuery(HarvestRequest request);

    List<HarvestResponse> getHarvestByUnitName(String name);

    List<String> getContacts(String contacts);

    void saveHarvest(Harvest harvest, List<PatentDTO> patents);

    List<String> getHoloder(String holder);

    List<Harvest> getRelatedHavestByCompanyId(Long id);

    List<Harvest> getHarvesByTechDomainLimit(String techDomain, int i);

    void updateHarvest(Harvest harvest, List<PatentDTO> patents);

    void updateUnitName(String oldUnitName, String unitName);
}
