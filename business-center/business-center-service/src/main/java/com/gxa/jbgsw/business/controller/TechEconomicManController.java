package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.TechEconomicManApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.TechEconomicMan;
import com.gxa.jbgsw.business.entity.TechEconomicManAppraise;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.protocol.enums.LevelEnum;
import com.gxa.jbgsw.business.protocol.enums.TechEconomicManLevelEnum;
import com.gxa.jbgsw.business.service.TechEconomicManAppraiseService;
import com.gxa.jbgsw.business.service.TechEconomicManService;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "技术经纪人管理")
public class TechEconomicManController implements TechEconomicManApi {
    @Resource
    TechEconomicManService techEconomicManService;
    @Resource
    TechEconomicManAppraiseService techEconomicManAppraiseService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<HavestDTO> getTechEconomicByLabel(String[] labels) {
        return null;
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        techEconomicManService.deleteBatchIds(ids);
    }

    @Override
    public void add(TechEconomicManDTO techEconomicManDTO) {
        TechEconomicMan techEconomicMan = mapperFacade.map(techEconomicManDTO, TechEconomicMan.class);

        techEconomicManService.save(techEconomicMan);
    }

    @Override
    public void update(TechEconomicManDTO techEconomicManDTO) {
        TechEconomicMan techEconomicMan = techEconomicManService.getById(techEconomicManDTO.getId());
        if(techEconomicMan != null){
            BeanUtils.copyProperties(techEconomicManDTO, techEconomicMan, CopyPropertionIngoreNull.getNullPropertyNames(techEconomicMan));
            techEconomicManService.updateById(techEconomicMan);
        }
    }

    @Override
    public PageResult<TechEconomicManResponse> pageQuery(TechEconomicManRequest request) {
        PageResult<TechEconomicManResponse> pages = new PageResult<>();

        PageResult<TechEconomicMan> pageResult = techEconomicManService.pageQuery(request);
        List<TechEconomicMan> techEconomics = pageResult.getList();
        if(CollectionUtils.isNotEmpty(techEconomics)){
            List<TechEconomicManResponse> responses = mapperFacade.mapAsList(techEconomics, TechEconomicManResponse.class);
            responses.forEach(s->{
                s.setLevelName(TechEconomicManLevelEnum.getNameByIndex(s.getLevel()));
            });
            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }

    @Override
    public TechEconomicManResponse getTechEconomicManById(Long id) {
        TechEconomicMan techEconomicMan = techEconomicManService.getById(id);
        TechEconomicManResponse techEconomicManResponse = mapperFacade.map(techEconomicMan, TechEconomicManResponse.class);

        if(techEconomicManResponse != null){
            if(techEconomicManResponse.getLevel() != null){
                techEconomicManResponse.setLevelName(LevelEnum.getNameByIndex(techEconomicManResponse.getLevel()));
            }
        }
        return techEconomicManResponse;
    }
}

