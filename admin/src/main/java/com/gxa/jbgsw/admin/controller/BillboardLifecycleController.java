package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.BillboardLifecycleFeignApi;
import com.gxa.jbgsw.business.client.BillboardLifecycleApi;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleResponse;
import com.gxa.jbgsw.common.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "需求跟进管理")
@RestController
@Slf4j
@ResponseBody
public class BillboardLifecycleController extends BaseController {
    @Resource
    BillboardLifecycleFeignApi billboardLifecycleFeignApi;

    @ApiOperation("需求跟进新增")
    @PostMapping("/billboard/lifecycle/add")
    void add(@RequestBody BillboardlifecycleDTO billboardlifecycleDTO){
        billboardlifecycleDTO.setCreateAt(new Date());
        billboardlifecycleDTO.setCreateBy(this.getUserId());
        billboardlifecycleDTO.setUserName(this.getUserNick());

        billboardLifecycleFeignApi.add(billboardlifecycleDTO);
    }

    @ApiOperation("需求跟进列表")
    @GetMapping("/billboard/lifecycle/list")
    List<BillboardlifecycleResponse> list(@RequestParam("pid") Long pid){
        return null;
    }

}
