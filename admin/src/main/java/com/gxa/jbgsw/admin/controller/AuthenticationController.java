package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.AuthenticationFeignApi;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "认证管理")
@RestController
@Slf4j
@ResponseBody
public class AuthenticationController extends BaseController {
    @Resource
    AuthenticationFeignApi authenticationFeignApi;

    @ApiOperation(value = "批量删除认证信息", notes = "批量删除认证信息")
    @PostMapping("/auth/info/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        authenticationFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("新增用户认证信息")
    @PostMapping("/auth/info/add")
    void add(@RequestBody UserDTO userDTO) throws BizException {

    }

}
