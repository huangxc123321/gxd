package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.business.client.BillboardTemporaryApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardTemporary;
import com.gxa.jbgsw.business.protocol.dto.BillboardDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryResponse;
import com.gxa.jbgsw.business.service.BillboardTemporaryService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Api(tags = "榜单导入管理")
public class BillboardTemporaryController implements BillboardTemporaryApi {
    @Resource
    BillboardTemporaryService billboardTemporaryService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(BillboardTemporaryDTO billboardTemporaryDTO) {

    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        billboardTemporaryService.deleteBatchIds(ids);
    }

    @Override
    public BillboardTemporaryDTO getById(Long id) {
        BillboardTemporary billboardTemporary = billboardTemporaryService.getById(id);
        BillboardTemporaryDTO billboardTemporaryDTO = mapperFacade.map(billboardTemporary, BillboardTemporaryDTO.class);

        return billboardTemporaryDTO;
    }

    @Override
    public void update(BillboardTemporaryDTO billboardTemporaryDTO) {
        BillboardTemporary billboardTemporary = billboardTemporaryService.getById(billboardTemporaryDTO.getId());
        BeanUtils.copyProperties(billboardTemporaryDTO, billboardTemporary);
        billboardTemporaryService.updateById(billboardTemporary);
    }

    @Override
    public PageResult<BillboardTemporaryResponse> pageQuery(BillboardTemporaryRequest request) {
        return billboardTemporaryService.pageQuery(request);
    }

    @Override
    public void batchInsert(BillboardTemporaryDTO[] batchList) {
        List<BillboardTemporaryDTO> list = Arrays.stream(batchList).collect(Collectors.toList());
        billboardTemporaryService.batchInsert(list);
    }

    @Override
    public void deleteByCreateBy(Long userId) {
        billboardTemporaryService.deleteByCreateBy(userId);
    }
}
