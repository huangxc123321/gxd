package com.gxa.jbgsw.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.entity.Billboardlifecycle;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleResponse;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/8/10 0010 18:08
 * @Version 2.0
 */
public interface BillboardLifecycleService extends IService<Billboardlifecycle> {
    List<BillboardlifecycleResponse> selectList(Long pid);
}
