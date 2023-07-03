package com.gxa.jbgsw.basis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.basis.entity.DictionaryType;
import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
public interface DictionaryTypeService extends IService<DictionaryType> {

    PageResult<DictionaryTypeResponse> pageQuery(DictionaryTypePageRequest request);

    List<DictionaryTypeResponse> list(DictionaryTypeRequest request);

    List<DictionaryResponse> getDictionaryByCode(DictionaryCodeRequest request);

    List<DictionaryResponse> getByCode(String code);
}
