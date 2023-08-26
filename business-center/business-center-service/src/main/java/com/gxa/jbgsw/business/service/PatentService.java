package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.Patent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.PatentDTO;

import java.util.List;

/**
 * <p>
 * 专利 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface PatentService extends IService<Patent> {

    List<PatentDTO> getPatentByPid(Long id);
}
