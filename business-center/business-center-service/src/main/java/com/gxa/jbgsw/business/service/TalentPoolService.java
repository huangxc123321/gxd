package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.TalentPool;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * <p>
 * 帅才库 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface TalentPoolService extends IService<TalentPool> {

    void deleteBatchIds(Long[] ids);

    void add(TalentPoolDTO talentPoolDTO);

    void updateTalentPool(TalentPoolDTO talentPoolDTO) ;

    List<TalentPool> getTalentPoolByTech(String key);

    PageResult<SearchTalentsResponse> queryTalents(SearchTalentsRequest searchTalentsRequest);

    PageResult<TalentPoolResponse> pageQuery(TalentPoolRequest request);

    List<String> getUnits(String unitName);

    List<TalentPool> getRelatedTalentByCompanyId(Long id);
}
