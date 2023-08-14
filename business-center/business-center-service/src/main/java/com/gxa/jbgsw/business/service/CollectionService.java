package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.Collection;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.*;

import java.util.List;

/**
 * <p>
 * 我的收藏 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface CollectionService extends IService<Collection> {

    void deleteCollection(CollectionDTO collectionDTO);

    Collection getCollection(Long pid, Long userId, Integer type);

    void deleteBatchByPid(List<Long> list);

    List<Collection> queryMyCollections(Long createBy);

    void deleteById(Long id);

    List<MyCollectionBillboardResponse> getMyCollectionBillboardResponse(Long createBy, Integer collectionType);

    List<MyHavestBillboardResponse> getMyHavestBillboardResponse(Long createBy, Integer collectionType);

    List<MypolicyResponse> getPolicys(Long createBy, Integer collectionType);

    Integer getCollections(Long userId);

    MyCollectionResponse pageQuery(MyCollectionRequest myCollectionRequest);
}
