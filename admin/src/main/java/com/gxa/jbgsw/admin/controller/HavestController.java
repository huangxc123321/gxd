package com.gxa.jbgsw.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.HavestFeignApi;
import com.gxa.jbgsw.business.protocol.dto.HarvestRequest;
import com.gxa.jbgsw.business.protocol.dto.HarvestResponse;
import com.gxa.jbgsw.business.protocol.dto.HavestDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.CompanyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "成果管理")
@RestController
@Slf4j
@ResponseBody
public class HavestController extends BaseController {

    @Resource
    HavestFeignApi havestFeignApi;

    @ApiOperation("新增成果信息")
    @PostMapping("/havest/add")
    void add(@RequestBody HavestDTO havestDTO) throws BizException {
        havestDTO.setCreateBy(this.getUserId());
        havestDTO.setCreateAt(new Date());

        havestFeignApi.add(havestDTO);
    }

    @ApiOperation(value = "批量删除成果", notes = "批量删除成果")
    @PostMapping("/havest/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        havestFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("修改成果信息")
    @PostMapping("/havest/update")
    void update(@RequestBody HavestDTO havestDTO) throws BizException {
        havestFeignApi.update(havestDTO);
    }

    @ApiOperation("获取成果列表")
    @PostMapping("/havest/pageQuery")
    PageResult<HarvestResponse> pageQuery(@RequestBody HarvestRequest request){
        PageResult<HarvestResponse> pageResult = havestFeignApi.pageQuery(request);
        log.info("Result：{}", JSONObject.toJSONString(pageResult));

        return pageResult;
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "成果ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/havest/getHavestById")
    public HavestDTO getHavestById(@RequestParam("id")Long id){
        return havestFeignApi.getHavestById(id);
    }


}
