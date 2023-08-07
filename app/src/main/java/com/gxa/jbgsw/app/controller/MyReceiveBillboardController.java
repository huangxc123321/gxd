package com.gxa.jbgsw.app.controller;

import com.gxa.jbgsw.business.protocol.dto.BillboardGainDTO;
import com.gxa.jbgsw.business.protocol.dto.MyReceiveBillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.MyReceiveBillboardResponse;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.app.feignapi.BillboardFeignApi;
import com.gxa.jbgsw.app.feignapi.BillboardGainFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户中心: 我的揭榜")
@RestController
@Slf4j
@ResponseBody
public class MyReceiveBillboardController extends BaseController {
    @Resource
    BillboardFeignApi billboardFeignApi;
    @Resource
    BillboardGainFeignApi billboardGainFeignApi;


    @ApiOperation("获取我揭榜的榜单列表")
    @PostMapping("/user/center/queryMyReceiveBillboard")
    MyReceiveBillboardResponse queryMyReceiveBillboard(@RequestBody MyReceiveBillboardRequest request){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        request.setUserId(userId);
        MyReceiveBillboardResponse response = billboardFeignApi.queryMyReceiveBillboard(request);

        return response;
    }

    @ApiOperation("查看我揭榜的榜单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/user/center/myReceiveBillboardDetail")
    public BillboardGainDTO myReceiveBillboardDetail(@RequestParam("id")Long id){
        return billboardGainFeignApi.getBillboardGainById(id);
    }




}
