package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.LastBillboardSetApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.BillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.DetailInfoDTO;
import com.gxa.jbgsw.business.protocol.dto.LastBillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.LastBillboardResponse;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.protocol.enums.IsTopEnum;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "最新榜单设置管理")
public class LastBillboardSetController implements LastBillboardSetApi {
    @Resource
    BillboardService billboardService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public PageResult<LastBillboardResponse> pageQuery(LastBillboardRequest request) {
        // PageResult<Billboard> pageResult = billboardService.LastBillboardSetData(request);

        PageResult<LastBillboardResponse> pages = new PageResult<>();

        PageResult<Billboard> pageResult = billboardService.LastBillboardSetData(request);
        List<Billboard> billboards = pageResult.getList();
        if(CollectionUtils.isNotEmpty(billboards)){
            List<LastBillboardResponse> responses = mapperFacade.mapAsList(billboards, LastBillboardResponse.class);
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
    public void deleteBatchIds(Long[] ids) {
        billboardService.deleteBatchIds(ids);
    }

    @Override
    public void cancelTop(Long id) {
        // 是否置顶： 0 不置顶 1 置顶
        billboardService.updateTop(id, IsTopEnum.NO_TOP.getCode());
    }

    @Override
    public void top(Long id) {
        // 是否置顶： 0 不置顶 1 置顶
        billboardService.updateTop(id, IsTopEnum.TOP.getCode());
    }

    @Override
    public DetailInfoDTO detail(Long id) {
        return null;
    }
}
