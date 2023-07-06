package com.gxa.jbgsw.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.business.protocol.dto.BillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.MyPublishBillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.MyPublishBillboardResponse;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.website.feignapi.BillboardFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户中心")
@RestController
@Slf4j
@ResponseBody
public class UserCenterController extends BaseController {
    @Resource
    BillboardFeignApi billboardFeignApi;


    @ApiOperation("获取我发布的榜单列表")
    @PostMapping("/user/center/queryMyPublish/")
    MyPublishBillboardResponse queryMyPublish(@RequestBody MyPublishBillboardRequest request){

        request.setUserId(this.getUserId());
        MyPublishBillboardResponse response = billboardFeignApi.queryMyPublish(request);

        return response;
    }

}
