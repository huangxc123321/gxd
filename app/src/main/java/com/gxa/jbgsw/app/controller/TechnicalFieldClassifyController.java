package com.gxa.jbgsw.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyPO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.app.feignapi.TechnicalFieldClassifyFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "技术领域分类管理")
@RestController
@Slf4j
@ResponseBody
public class TechnicalFieldClassifyController extends BaseController {
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation("根据ID获取技术领域分类: 获取所有分类： pid：0， 获取某个分类把这个分类得ID值作为pid的值传入即可。")
    @GetMapping("/techical/field/classify/getAllById")
    ApiResult<List<TechnicalFieldClassifyPO>> getCommentById(@RequestParam("pid") Long pid) throws BizException {
        List<TechnicalFieldClassifyPO> responress = technicalFieldClassifyFeignApi.getAllById(pid);

        ApiResult<List<TechnicalFieldClassifyPO>> apiResult = new ApiResult();
        apiResult.setData(responress);

        return apiResult;
    }


    @ApiOperation("获取pid下的所有分类， pid 不能是0， 否则会嵌套死")
    @GetMapping("/techical/field/classify/getAllByParentId")
    ApiResult<List<TechnicalFieldClassifyDTO>> getAllByParentId(@RequestParam("pid") Long pid) throws BizException {
        List<TechnicalFieldClassifyDTO> responress = technicalFieldClassifyFeignApi.getAllByParentId(pid);

        ApiResult<List<TechnicalFieldClassifyDTO>> apiResult = new ApiResult();
        apiResult.setData(responress);

        return apiResult;
    }



    @ApiOperation("获取所有数据")
    @GetMapping("/techical/field/classify/getAll")
    ApiResult<List<TechnicalFieldClassifyDTO>> getAll() throws BizException {

        String json = stringRedisTemplate.opsForValue().get(RedisKeys.TECH_DOMAIN_VALUE);
        List<TechnicalFieldClassifyDTO> responress = JSONArray.parseArray(json, TechnicalFieldClassifyDTO.class);

        ApiResult<List<TechnicalFieldClassifyDTO>> apiResult = new ApiResult();
        apiResult.setData(responress);

        return apiResult;
    }


}
