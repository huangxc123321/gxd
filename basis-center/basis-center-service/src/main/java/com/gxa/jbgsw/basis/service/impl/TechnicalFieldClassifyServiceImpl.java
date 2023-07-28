package com.gxa.jbgsw.basis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.basis.entity.TechnicalFieldClassify;
import com.gxa.jbgsw.basis.mapper.TechnicalFieldClassifyMapper;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.service.TechnicalFieldClassifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TechnicalFieldClassifyServiceImpl extends ServiceImpl<TechnicalFieldClassifyMapper, TechnicalFieldClassify> implements TechnicalFieldClassifyService {
    @Resource
    TechnicalFieldClassifyMapper technicalFieldClassifyMapper;

    @Override
    public List<TechnicalFieldClassifyDTO> getAllById(Long pid) {
        return technicalFieldClassifyMapper.getAllById(pid);
    }
}
