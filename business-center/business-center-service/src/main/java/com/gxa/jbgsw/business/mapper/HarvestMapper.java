package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.Harvest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.HarvestRequest;

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

    List<Harvest> pageQuery(HarvestRequest request);
}
