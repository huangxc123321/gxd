package com.gxa.jbgsw.business.controller;


import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.BillboardApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.BillboardDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.DetailInfoDTO;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "榜单管理")
public class BillboardController implements BillboardApi {
    @Resource
    BillboardService billboardService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(BillboardDTO billboardDTO) {
        Billboard billboard = mapperFacade.map(billboardDTO, Billboard.class);
        billboard.setCreateAt(new Date());

        billboardService.save(billboard);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        billboardService.deleteBatchIds(ids);
    }

    @Override
    public void cancelTop(Long id) {
        // 是否置顶： 0 不置顶 1 置顶
        billboardService.updateTop(id, 0);
    }

    @Override
    public void batchIdsTop(Long[] ids) {
        // 是否置顶： 0 不置顶 1 置顶
        billboardService.batchIdsTop(ids, 1);
    }

    @Override
    public PageResult<BillboardResponse> pageQuery(BillboardRequest request) {
        PageResult<BillboardResponse> pages = new PageResult<>();

        PageResult<Billboard> pageResult = billboardService.pageQuery(request);
        List<Billboard> billboards = pageResult.getList();
        if(CollectionUtils.isNotEmpty(billboards)){
            List<BillboardResponse> responses = mapperFacade.mapAsList(billboards, BillboardResponse.class);
            responses.forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    // 工信大类名称
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
                // 状态名称
                s.setStatusName(BillboardStatusEnum.getNameByIndex(s.getStatus()));
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
    public void updateSeqNo(Long id, Integer seqNo) {
        billboardService.updateSeqNo(id, seqNo);
    }

    @Override
    public DetailInfoDTO detail(Long id) {
        return null;
    }
}

