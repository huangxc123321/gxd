package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.AuthenticationFeignApi;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.AuthenticationDTO;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "认证管理")
@RestController
@Slf4j
@ResponseBody
public class AuthenticationController extends BaseController {
    @Resource
    AuthenticationFeignApi authenticationFeignApi;

    @ApiOperation(value = "批量删除认证信息", notes = "批量删除认证信息")
    @PostMapping("/authentication/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        // authenticationFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("新增用户认证信息")
    @PostMapping("/authentication/add")
    void add(@RequestBody AuthenticationDTO authenticationDTO) throws BizException {
        authenticationFeignApi.add(authenticationDTO);
    }

    @ApiOperation("根据用户ID获取用户认证信息")
    @GetMapping("/authentication/getAuthInfoByUserId")
    AuthenticationDTO getAuthInfoByUserId(@RequestParam("userId") Long userId){
        return authenticationFeignApi.getAuthInfoByUserId(userId);
    }

}
