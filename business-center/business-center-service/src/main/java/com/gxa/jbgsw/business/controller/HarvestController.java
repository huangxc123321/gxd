package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.HarvestApi;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "成果管理")
public class HarvestController implements HarvestApi {
    @Resource
    HarvestService harvestService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    MapperFacade mapperFacade;


    @Override
    public PageResult<HarvestResponse> pageQuery(HarvestRequest request) {
        PageResult<HarvestResponse> pages = new PageResult<>();

        PageResult<Harvest> pageResult = harvestService.pageQuery(request);
        List<Harvest> harvests = pageResult.getList();
        if(CollectionUtils.isNotEmpty(harvests)){
            List<HarvestResponse> responses = mapperFacade.mapAsList(harvests, HarvestResponse.class);
            responses.forEach(s->{
                // TODO: 2023/7/4 0004 暂时不转换
                
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(""));
                if(dictionaryDTO != null){
              

                }
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
    public void add(HavestDTO havestDTO) {
        Harvest harvest = mapperFacade.map(havestDTO, Harvest.class);
        harvest.setCreateAt(new Date());

        harvestService.save(harvest);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        harvestService.deleteBatchIds(ids);
    }

    @Override
    public void update(HavestDTO havestDTO) {
        Harvest harvest = harvestService.getById(havestDTO.getId());

        // havestDTO有null就不需要替换harvest
        BeanUtils.copyProperties(havestDTO, harvest, CopyPropertionIngoreNull.getNullPropertyNames(harvest));
        harvestService.updateById(harvest);
    }

    @Override
    public List<HavestDTO> getHarvesByHolder(String holder) {
        List<Harvest> harvests = harvestService.getHarvesByHolder(holder);

        return mapperFacade.mapAsList(harvests, HavestDTO.class);
    }

    @Override
    public DetailInfoDTO detail(Long id) {
        // TODO: 2023/7/4 0004 待完成 
        
        return null;
    }

    @Override
    public HavestDTO getHavestById(Long id) {
        Harvest harvest = harvestService.getById(id);
        HavestDTO havestDTO = mapperFacade.map(harvest, HavestDTO.class);

        return havestDTO;
    }

}

