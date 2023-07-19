package com.gxa.jbgsw.business.controller;


import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.BillboardApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
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
        Billboard billboard = billboardService.getById(id);
        DetailInfoDTO detailInfoDTO = mapperFacade.map(billboard, DetailInfoDTO.class);
        if(detailInfoDTO != null && detailInfoDTO.getCategories() != null){
            // 行业名称
            DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(detailInfoDTO.getCategories()));
            if(dictionaryDTO != null){
                // 工信大类名称
                detailInfoDTO.setCategoriesName(dictionaryDTO.getDicValue());
            }
        }

        return detailInfoDTO;
    }

    @Override
    public MyPublishBillboardResponse queryMyPublish(MyPublishBillboardRequest request) {
        MyPublishBillboardResponse response = new MyPublishBillboardResponse();

        // 先统计另外类型的总数
        Integer trueType = BillboardTypeEnum.GOV_BILLBOARD.getCode();
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            trueType = BillboardTypeEnum.BUS_BILLBOARD.getCode();
        }
        int num = billboardService.getPublishNum(request.getUserId(), trueType);

        // 政府榜
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            // 获取我发布的政府榜列表
            PageResult<MyPublishBillboardInfo> pageResult = billboardService.queryMyPublish(request);

            response.setBillboards(pageResult.getList());
            response.setPageNum(pageResult.getPageNum());
            response.setPageSize(pageResult.getPageSize());
            response.setSize(pageResult.getSize());
            response.setTotal(pageResult.getTotal());
            response.setPages(pageResult.getPages());
            response.setBusBillboardsNum(num);
        }else{
            // 获取我发布的企业榜列表
            PageResult<MyPublishBillboardInfo> pageResult = billboardService.queryMyPublish(request);

            response.setBillboards(pageResult.getList());
            response.setPageNum(pageResult.getPageNum());
            response.setPageSize(pageResult.getPageSize());
            response.setSize(pageResult.getSize());
            response.setTotal(pageResult.getTotal());
            response.setPages(pageResult.getPages());
            response.setBusBillboardsNum(num);
            response.setGovBillboardsNum(num);
        }

        return response;
    }

    @Override
    public void updateMyBillboard(BillboardDTO billboardDTO) {
        billboardService.updateMyBillboard(billboardDTO);
    }

    @Override
    public MyReceiveBillboardResponse queryMyReceiveBillboard(MyReceiveBillboardRequest request) {
        MyReceiveBillboardResponse response = new MyReceiveBillboardResponse();

        // 先统计另外类型的总数
        Integer trueType = BillboardTypeEnum.GOV_BILLBOARD.getCode();
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            trueType = BillboardTypeEnum.BUS_BILLBOARD.getCode();
        }
        int num = billboardService.getMyReceiveBillboard(request.getUserId(), trueType);

        // 政府榜
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            // 获取我发布的政府榜列表
            PageResult<MyReceiveBillboardInfo> pageResult = billboardService.queryMyReceiveBillboard(request);

            response.setBillboards(pageResult.getList());
            response.setPageNum(pageResult.getPageNum());
            response.setPageSize(pageResult.getPageSize());
            response.setSize(pageResult.getSize());
            response.setTotal(pageResult.getTotal());
            response.setPages(pageResult.getPages());
            response.setBusBillboardsNum(num);
        }else{
            // 获取我发布的企业榜列表
            PageResult<MyReceiveBillboardInfo> pageResult = billboardService.queryMyReceiveBillboard(request);

            response.setBillboards(pageResult.getList());
            response.setPageNum(pageResult.getPageNum());
            response.setPageSize(pageResult.getPageSize());
            response.setSize(pageResult.getSize());
            response.setTotal(pageResult.getTotal());
            response.setPages(pageResult.getPages());
            response.setBusBillboardsNum(num);
            response.setGovBillboardsNum(num);
        }

        return response;
    }
}

