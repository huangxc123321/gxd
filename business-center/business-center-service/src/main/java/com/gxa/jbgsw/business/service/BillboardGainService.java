package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.BillboardGain;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 接榜 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface BillboardGainService extends IService<BillboardGain> {

    List<BillboardGain> getBillboardGainByPid(Long id);

    boolean getIsGain(Long pid, Long userId);
}
