package com.gxa.jbgsw.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardTemporary;
import com.gxa.jbgsw.business.protocol.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 榜单信息 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface BillboardTemporaryMapper extends BaseMapper<BillboardTemporary> {

    List<BillboardTemporary> pageQuery(BillboardTemporaryRequest request);
}
