package com.gxa.jbgsw.basis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.basis.entity.Dictionary;
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
public interface DictionaryService extends IService<Dictionary> {

    void insert(DictionaryDTO dictionaryDTO);

    void updateDictionary(DictionaryDTO dictionaryDTO);

    PageResult<DictionaryResponse> pageQuery(DictionaryRequest request);

    DictionaryDTO getDictionaryById(Long id);

    void deleteBatchIds(Long[] ids);

    DictionaryDTO getDictionaryByCodeAndTypeCode(DictionaryValueQueryRequest request);

    void initDictionary();

    List<DictionaryTypeResponse> list(DictionaryTypeRequest request);

    List<DictionaryResponse> getByCode(String code);

}
