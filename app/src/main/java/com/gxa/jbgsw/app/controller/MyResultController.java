package com.gxa.jbgsw.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.app.feignapi.HavestFeignApi;
import com.gxa.jbgsw.business.protocol.dto.HarvestRequest;
import com.gxa.jbgsw.business.protocol.dto.HarvestResponse;
import com.gxa.jbgsw.business.protocol.dto.HavestDTO;
import com.gxa.jbgsw.business.protocol.dto.MyHarvestRequest;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "用户中心: 我的成果")
@RestController
@Slf4j
@ResponseBody
public class MyResultController extends BaseController {
    @Resource
    HavestFeignApi havestFeignApi;


    @ApiOperation("获取我的成果列表")
    @PostMapping("/havest/pageQuery")
    PageResult<HarvestResponse> pageQuery(@RequestBody MyHarvestRequest myHarvestRequest){
        HarvestRequest harvestRequest = new HarvestRequest();
        harvestRequest.setCreateBy(this.getUserId());
        harvestRequest.setPageNum(myHarvestRequest.getPageNum());
        harvestRequest.setPageSize(myHarvestRequest.getPageSize());
        PageResult<HarvestResponse> pageResult = havestFeignApi.pageQuery(harvestRequest);
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

    @ApiOperation("修改成果信息")
    @PostMapping("/havest/update")
    void update(@RequestBody HavestDTO havestDTO) throws BizException {
        havestFeignApi.update(havestDTO);
    }

    @ApiOperation(value = "删除成果", notes = "删除成果")
    @PostMapping("/havest/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        havestFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("发布成果")
    @PostMapping("/havest/add")
    void add(@RequestBody HavestDTO havestDTO) throws BizException {
        havestDTO.setCreateBy(this.getUserId());
        havestDTO.setCreateAt(new Date());

        havestFeignApi.add(havestDTO);
    }


}
