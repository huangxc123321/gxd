package com.gxa.jbgsw.business.mapper;

import com.gxa.jbgsw.business.entity.Collection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.protocol.dto.MyCollectionBillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.MyHavestBillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.MypolicyResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 我的收藏 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface CollectionMapper extends BaseMapper<Collection> {

    List<MyCollectionBillboardResponse> getMyCollectionBillboardResponse(@Param("createBy") Long createBy, @Param("collectionType") Integer collectionType);

    List<MyHavestBillboardResponse> getMyHavestBillboardResponse(@Param("createBy") Long createBy,  @Param("collectionType") Integer collectionType);

    List<MypolicyResponse> getPolicys(@Param("createBy") Long createBy,  @Param("collectionType") Integer collectionType);
}
