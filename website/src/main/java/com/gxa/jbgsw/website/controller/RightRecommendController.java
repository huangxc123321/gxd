package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.business.protocol.dto.RelateDTO;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.website.feignapi.IndexFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "页面右边推荐管理")
@RestController
@Slf4j
@ResponseBody
public class RightRecommendController extends BaseController {
    @Resource
    IndexFeignApi indexFeignApi;

    @ApiOperation("根据榜单ID获取相关成果、帅才推荐、榜单推荐信息， （榜单详情页使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByBillboardId")
    ApiResult<RelateDTO> getRelatedByBillboardId(@RequestParam(value = "id") Long id) {
        ApiResult<RelateDTO> apiResult = new ApiResult<>();
        RelateDTO relateDTO = indexFeignApi.getRelatedByBillboardId(id);
        apiResult.setData(relateDTO);

        return apiResult;
    }

    @ApiOperation("根据成果ID获取相关成果、帅才推荐、榜单推荐信息， （成果详情页使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "成果ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByHarvestId")
    ApiResult<RelateDTO>  getRelatedByHarvestId(@RequestParam(value = "id") Long id) {
        ApiResult<RelateDTO> apiResult = new ApiResult<>();
        RelateDTO relateDTO = indexFeignApi.getRelatedByHarvestId(id);
        apiResult.setData(relateDTO);

        return apiResult;
    }

    @ApiOperation("根据帅才ID获取相关成果、帅才推荐、榜单推荐信息， （帅才详情页使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "帅才ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByTalentId")
    ApiResult<RelateDTO>  getRelatedByTalentId(@RequestParam(value = "id") Long id) {
        ApiResult<RelateDTO> apiResult = new ApiResult<>();
        RelateDTO relateDTO = indexFeignApi.getRelatedByTalentId(id);
        apiResult.setData(relateDTO);

        return apiResult;
    }

}
