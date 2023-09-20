package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.ConstantsUtils;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UpdatePasswordDTO;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.enums.UserPlatformEnum;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.UserFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(tags = "用户管理")
@RestController
@Slf4j
@ResponseBody
public class UserController extends BaseController {
    private int expireTime = 2592000;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation("通过id获取用户信息")
    @GetMapping("/user/getUserById")
    UserResponse getUserById(@RequestParam("id") Long id){
        return userFeignApi.getUserById(id);
    }

    @ApiOperation("新增用户信息")
    @PostMapping("/user/add")
    void add(@RequestBody UserDTO userDTO) throws BizException {
        boolean isValidate = false;
        // 判断验证码是否正确
        Object value = stringRedisTemplate.opsForValue().get(RedisKeys.USER_VALIDATE_CODE+userDTO.getMobile());
        if(value != null){
            if(userDTO.getValidateCode() != null && userDTO.getValidateCode().equals(value.toString())){
                isValidate = true;
            }
        }
        if(!isValidate){
            throw new BizException(UserErrorCode.LOGIN_VALIDATECODE_IS_ERROR);
        }

        // 判断手机号码是否注册
        UserResponse existUserDTO = userFeignApi.getUserByMobile(userDTO.getMobile());
        if(existUserDTO != null){
            throw new BizException(UserErrorCode.USER_PHONE_IS_EXISTS);
        }

        userDTO.setCreateBy(this.getUserId());
        // 设置默认密码: 123456
        userDTO.setPassword(ConstantsUtils.defalutMd5Password);
        userDTO.setPlatform(UserPlatformEnum.APP.getCode());
        userFeignApi.add(userDTO);
    }

    @ApiOperation("修改用户信息: 头像、性别、昵称、地区, 电话")
    @PostMapping("/user/update")
    void update(@RequestBody UserDTO userDTO) throws BizException {
        if(userDTO == null || userDTO.getId() == null){
            throw new BizException(UserErrorCode.USER_PARAMS_ERROR);
        }
        userDTO.setUpdateBy(this.getUserId());
        userDTO.setPlatform(UserPlatformEnum.APP.getCode());
        userFeignApi.update(userDTO);
        String  token = this.getToken();
        if(StrUtil.isNotBlank(token)){
            UserResponse userResponse = userFeignApi.getUserById(userDTO.getId());

            // token放到redis中
            stringRedisTemplate.opsForValue().set(RedisKeys.USER_TOKEN+token, String.valueOf(userResponse.getId()), expireTime, TimeUnit.SECONDS);
            // 存储用户信息
            stringRedisTemplate.opsForValue().set(RedisKeys.USER_INFO+userResponse.getId(), JSONObject.toJSONString(userResponse), expireTime, TimeUnit.SECONDS);
        }
    }

    @ApiOperation("修改用户密码")
    @PostMapping("/user/update/password")
    void updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) throws BizException {
        userFeignApi.updatePassword(updatePasswordDTO);
    }

    @ApiOperation("测试发送短信")
    @GetMapping("/user/send/msg")
   void testSmsSend() throws Exception {
       userFeignApi.sendSms();
   }
}
