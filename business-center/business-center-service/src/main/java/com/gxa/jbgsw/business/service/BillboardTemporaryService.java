package com.gxa.jbgsw.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.entity.BillboardTemporary;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryResponse;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;


public interface BillboardTemporaryService  extends IService<BillboardTemporary> {
    void batchInsert(List<BillboardTemporaryDTO> list);

    PageResult<BillboardTemporaryResponse> pageQuery(BillboardTemporaryRequest request);

    void deleteBatchIds(Long[] ids);

    void deleteByCreateBy(Long userId);

    void updateStatusByCreateBy(Long createBy, Integer status);

    void deleteByCreateByAndIds(Long createBy, Long[] ids);
}
