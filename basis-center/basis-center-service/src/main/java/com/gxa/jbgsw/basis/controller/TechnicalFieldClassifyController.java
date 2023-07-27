package com.gxa.jbgsw.basis.controller;

import com.gxa.jbgsw.basis.client.TechnicalFieldClassifyApi;
import com.gxa.jbgsw.basis.entity.TechnicalFieldClassify;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.service.TechnicalFieldClassifyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api("技术领域分类管理")
public class TechnicalFieldClassifyController implements TechnicalFieldClassifyApi {
    @Resource
    TechnicalFieldClassifyService technicalFieldClassifyService;

    @Override
    public List<TechnicalFieldClassifyDTO> getAll() {
        return null;
    }

    @Override
    public List<TechnicalFieldClassifyDTO> getAllById(Long id) {
        return technicalFieldClassifyService.getAllById(id);
    }
}
