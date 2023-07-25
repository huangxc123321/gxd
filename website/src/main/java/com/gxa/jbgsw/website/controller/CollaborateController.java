package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.business.protocol.dto.CollaborateDTO;
import com.gxa.jbgsw.business.protocol.dto.HavestDTO;
import com.gxa.jbgsw.business.protocol.enums.CollaborateTypeEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.website.feignapi.CollaborateFeignApi;
import com.gxa.jbgsw.website.feignapi.HavestFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "合作管理")
@RestController
@Slf4j
public class CollaborateController extends BaseController {
    @Resource
    CollaborateFeignApi collaborateFeignApi;
    @Resource
    HavestFeignApi havestFeignApi;


    @ApiOperation("新增合作")
    @PostMapping("/collaborate/add")
    void add(@RequestBody CollaborateDTO collaborateDTO) throws BizException {
        collaborateDTO.setEffectAt(new Date());
        collaborateDTO.setLaunchUserId(this.getUserId());
        collaborateDTO.setLaunchUserName(this.getUserNick());
        // 合作类型：0 成果合作  1 需求合作
        if(CollaborateTypeEnum.GAIN.getCode().equals(collaborateDTO.getType())){
            HavestDTO havest = havestFeignApi.getHavestById(collaborateDTO.getPid());
            collaborateDTO.setHarvestUserId(havest.getId());
        }

        collaborateFeignApi.add(collaborateDTO);
    }


}
