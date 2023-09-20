package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.UserFeignApi;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.*;
import com.gxa.jbgsw.user.protocol.dto.*;
import com.gxa.jbgsw.user.protocol.enums.UserPlatformEnum;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Api(tags = "用户管理")
@RestController
@Slf4j
@ResponseBody
public class UserFontController extends BaseController {
    private int expireTime = 2592000;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation("获取用户详情")
    @GetMapping("/user/getUserById")
    UserResponse getUserById(@RequestParam("id") Long id){
        return userFeignApi.getUserById(id);
    }

    @ApiOperation("获取用户列表")
    @PostMapping("/user/pageQuery")
    PageResult<UserResponse> pageQuery(@RequestBody UserRequest request){
        request.setPlatform(UserPlatformEnum.ADMIN.getCode());
        PageResult<UserResponse> pageResult = userFeignApi.pageQuery(request);
        log.info("Result：{}", JSONObject.toJSONString(pageResult));

        return pageResult;
    }

    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    @PostMapping("/user/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        userFeignApi.deleteBatchIds(ids);
    }


    @ApiOperation("后台新增用户信息")
    @PostMapping("/user/addAdmin")
    void addAdmin(@RequestBody UserDTO userDTO) throws BizException {
        boolean isValidate = false;

        // 判断手机号码是否注册
        UserRequest userRequest = new UserRequest();
        userRequest.setSearchFiled(userDTO.getMobile());
        PageResult<UserResponse> pageResult = userFeignApi.pageQuery(userRequest);
        if(pageResult.getTotal()>0){
            throw new BizException(UserErrorCode.USER_PHONE_IS_EXISTS);
        }

        userDTO.setCreateBy(this.getUserId());
        // 设置默认密码: 123456
        userDTO.setPassword(ConstantsUtils.defalutMd5Password);
        userDTO.setPlatform(UserPlatformEnum.ADMIN.getCode());
        userFeignApi.add(userDTO);
    }


    @Deprecated
    @ApiOperation("新增用户信息(已经弃用)")
    @PostMapping("/user/add")
    void add(@RequestBody UserDTO userDTO) throws BizException {
        boolean isValidate = false;
        // 判断验证码是否正确
        System.out.println("key:>"+RedisKeys.USER_VALIDATE_CODE+userDTO.getMobile());
        Object value = stringRedisTemplate.opsForValue().get(RedisKeys.USER_VALIDATE_CODE+userDTO.getMobile());
        System.out.println("value: "+value);
        if(value != null){
            if(userDTO.getValidateCode() != null && userDTO.getValidateCode().equals(value.toString())){
                isValidate = true;
            }
        }
        if(!isValidate){
            throw new BizException(UserErrorCode.LOGIN_VALIDATECODE_IS_ERROR);
        }

        // 判断手机号码是否注册
        UserRequest userRequest = new UserRequest();
        userRequest.setSearchFiled(userDTO.getMobile());
        PageResult<UserResponse> pageResult = userFeignApi.pageQuery(userRequest);
        if(pageResult.getTotal()>0){
            throw new BizException(UserErrorCode.USER_PHONE_IS_EXISTS);
        }

        userDTO.setCreateBy(this.getUserId());
        // 设置默认密码: 123456
        userDTO.setPassword(ConstantsUtils.defalutMd5Password);
        userDTO.setPlatform(UserPlatformEnum.ADMIN.getCode());
        userFeignApi.add(userDTO);
    }

    @ApiOperation("修改用户信息: 头像、性别、昵称、地区")
    @PostMapping("/user/update")
    void update(@RequestBody UserDTO userDTO) throws BizException {
        if(userDTO == null || userDTO.getId() == null){
            throw new BizException(UserErrorCode.USER_PARAMS_ERROR);
        }
        userDTO.setUpdateBy(this.getUserId());
        userDTO.setPlatform(UserPlatformEnum.ADMIN.getCode());
        userFeignApi.updateUserAdmin(userDTO);

        String token = this.getToken();
        if(StrUtil.isNotBlank(token)){
            UserResponse userResponse = userFeignApi.getUserById(userDTO.getId());

            // token放到redis中
            stringRedisTemplate.opsForValue().set(RedisKeys.USER_TOKEN+token, String.valueOf(userResponse.getId()), expireTime, TimeUnit.SECONDS);
            // 存储用户信息
            stringRedisTemplate.opsForValue().set(RedisKeys.USER_INFO+userResponse.getId(), JSONObject.toJSONString(userResponse), expireTime, TimeUnit.SECONDS);
        }
    }

    @ApiOperation("修改用户使用状态: 使用状态： 0 已使用  1 停用")
    @GetMapping("/user/update/useStatus")
    void updateUseStatus(@RequestParam("id")Long id, @RequestParam("useStauts")Integer useStauts) throws BizException {
        userFeignApi.updateUseStatus(id, useStauts);
    }

    @ApiOperation("修改用户密码")
    @PostMapping("/user/update/updateAdminPassword")
    void updatePassword(@RequestBody UpdateAdminPasswordDTO updateAdminPasswordDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        updateAdminPasswordDTO.setId(userId);
        userFeignApi.updateAdminPassword(updateAdminPasswordDTO);
    }

}
