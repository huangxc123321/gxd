package com.gxa.jbgsw.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.basis.entity.TechnicalFieldClassify;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TechnicalFieldClassifyMapper  extends BaseMapper<TechnicalFieldClassify> {
    //List<TechnicalFieldClassifyDTO> getAllById(@Param("pid") Long pid);

    List<TechnicalFieldClassifyPO> getAllByPid(@Param("pid") Long pid);

    List<TechnicalFieldClassifyDTO> getAllByParentId(@Param("pid") Long pid);

    List<TechnicalFieldClassifyDTO> getAll(@Param("pid") Long pid);
}
