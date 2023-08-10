package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.business.client.BillboardLifecycleApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.Billboardlifecycle;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleResponse;
import com.gxa.jbgsw.business.service.BillboardLifecycleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "需求跟进管理")
public class BillboardLifecycleController implements BillboardLifecycleApi {
    @Resource
    BillboardLifecycleService billboardLifecycleService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(BillboardlifecycleDTO billboardlifecycleDTO) {
        Billboardlifecycle billboardlifecycle = mapperFacade.map(billboardlifecycleDTO, Billboardlifecycle.class);
        billboardLifecycleService.save(billboardlifecycle);
    }

    @Override
    public List<BillboardlifecycleResponse> list(Long pid) {
        return billboardLifecycleService.selectList(pid);
    }
}
