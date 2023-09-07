package com.gxa.jbgsw.basis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.basis.entity.TechnicalFieldClassify;
import com.gxa.jbgsw.basis.mapper.TechnicalFieldClassifyMapper;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyPO;
import com.gxa.jbgsw.basis.service.TechnicalFieldClassifyService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TechnicalFieldClassifyServiceImpl extends ServiceImpl<TechnicalFieldClassifyMapper, TechnicalFieldClassify> implements TechnicalFieldClassifyService {
    @Resource
    TechnicalFieldClassifyMapper technicalFieldClassifyMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<TechnicalFieldClassifyPO> getAllById(Long pid) {
        List<TechnicalFieldClassifyPO> pos =  technicalFieldClassifyMapper.getAllByPid(pid);
        return pos;
    }

    @Override
    public Long insert(TechnicalFieldClassifyPO po) {
        TechnicalFieldClassify technicalFieldClassify = mapperFacade.map(po, TechnicalFieldClassify.class);
        technicalFieldClassifyMapper.insert(technicalFieldClassify);
        Long id = technicalFieldClassify.getId();

        return id;
    }

    @Override
    public List<TechnicalFieldClassifyDTO> getAllByParentId(Long pid) {
        return technicalFieldClassifyMapper.getAllByParentId(pid);
    }
}
