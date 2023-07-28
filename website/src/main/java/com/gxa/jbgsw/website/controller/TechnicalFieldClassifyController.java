package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.website.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    @ApiOperation("根据ID获取技术领域分类: 获取所有分类： pid：-1， 获取某个分类把这个分类得ID值作为pid的值传入即可。")
    @GetMapping("/techical/field/classify/getAllById")
    ApiResult<List<TechnicalFieldClassifyDTO>> getCommentById(@RequestParam("pid") Long pid) throws BizException {
        List<TechnicalFieldClassifyDTO> responress = technicalFieldClassifyFeignApi.getAllById(pid);

        ApiResult<List<TechnicalFieldClassifyDTO>> apiResult = new ApiResult();
        apiResult.setData(responress);

        return apiResult;
    }

}
