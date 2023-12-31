package com.gxa.jbgsw.basis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.basis.entity.ProvinceCityDistrict;
import com.gxa.jbgsw.basis.entity.TechnicalFieldClassify;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyPO;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/27 0027 16:16
 * @Version 2.0
 */
public interface TechnicalFieldClassifyService extends IService<TechnicalFieldClassify> {
    List<TechnicalFieldClassifyPO> getAllById(Long pid);

    Long insert(TechnicalFieldClassifyPO po);

    List<TechnicalFieldClassifyDTO> getAllByParentId(Long pid);
}
