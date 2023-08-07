package com.gxa.jbgsw.app.controller;

import com.gxa.jbgsw.business.protocol.dto.AppMessageRequest;
import com.gxa.jbgsw.business.protocol.dto.AppMessageResponse;
import com.gxa.jbgsw.common.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "APP我的消息管理")
@RestController
@Slf4j
@ResponseBody
public class AppMessageController extends BaseController {

    @ApiOperation("获取我的消息")
    @PostMapping("/msg/pageQuery")
    AppMessageResponse pageQUery(@RequestBody AppMessageRequest request){

        return null;
    }


}
