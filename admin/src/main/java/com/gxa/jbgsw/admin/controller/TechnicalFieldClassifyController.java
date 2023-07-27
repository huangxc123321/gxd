package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.basis.client.TechnicalFieldClassifyApi;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.protocol.dto.CommentResponse;
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

    @ApiOperation("根据ID获取技术领域分类")
    @GetMapping("/techical/field/classify/getAllById")
    ApiResult<List<TechnicalFieldClassifyDTO>> getCommentById(@RequestParam("id") Long id) throws BizException {
        List<TechnicalFieldClassifyDTO> responress = technicalFieldClassifyFeignApi.getAllById(id);

        ApiResult<List<TechnicalFieldClassifyDTO>> apiResult = new ApiResult();
        apiResult.setData(responress);

        return apiResult;
    }

}
