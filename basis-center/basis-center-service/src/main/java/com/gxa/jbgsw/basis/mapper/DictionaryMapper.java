package com.gxa.jbgsw.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.basis.entity.Dictionary;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryValueQueryRequest;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {

    DictionaryDTO getDictionaryByCodeAndTypeCode(DictionaryValueQueryRequest request);
}
