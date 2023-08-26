package com.gxa.jbgsw.app.controller;

import com.gxa.jbgsw.business.protocol.dto.TalentPoolDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.app.feignapi.TalentPoolFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "帅才库管理")
@RestController
@Slf4j
@ResponseBody
public class TalentPoolController extends BaseController {
    @Resource
    TalentPoolFeignApi talentPoolFeignApi;

    @ApiOperation("新增帅才信息")
    @PostMapping("/talent/pool/add")
    void add(@RequestBody TalentPoolDTO talentPoolDTO) throws BizException {
        talentPoolDTO.setCreateBy(this.getUserId());

        talentPoolFeignApi.add(talentPoolDTO);
    }

    @ApiOperation("获取所在单位")
    @GetMapping("/talent/pool/getUnits")
    List<String> getUnits(@RequestParam("unitName") String unitName){
        return talentPoolFeignApi.getUnits(unitName);
    }

}
