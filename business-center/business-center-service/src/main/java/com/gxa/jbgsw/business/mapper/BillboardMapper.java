package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.Billboard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.BillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.LastBillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.MyPublishBillboardInfo;
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
public interface BillboardMapper extends BaseMapper<Billboard> {

    List<Billboard> pageQuery(BillboardRequest request);

    List<Billboard> LastBillboardSetData(LastBillboardRequest request);

    List<MyPublishBillboardInfo> queryMyPublish(@Param("userId") Long userId, @Param("type") Integer type);
}
