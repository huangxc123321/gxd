package com.gxa.jbgsw.user.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.common.utils.RedisUtils;
import com.gxa.jbgsw.user.client.LoginApi;
import com.gxa.jbgsw.user.protocol.dto.LoginRequest;
import com.gxa.jbgsw.user.protocol.dto.RolePO;
import com.gxa.jbgsw.user.protocol.dto.SmsMessageDTO;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.user.service.*;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Api(tags = "用户登录管理")
@RestController
@Slf4j
public class LoginController implements LoginApi {
    private int expireTime = 2592000;

    @Resource
    LoginService loginService;
    @Resource
    UserService userService;
    @Resource
    SmsService smsService;
    @Resource
    UserRoleService userRoleService;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public String getValidateCode(String mobile) throws Exception {

        SmsMessageDTO smsMessageDTO = new SmsMessageDTO();
        smsMessageDTO.setMobile(mobile);
        // 发送短信的类型：type: 0 验证, 1 其它
        smsMessageDTO.setType(0);

        // long code = this.rand();

        //// 为测试需要，
        long code = 6666L;
        System.out.println("code: "+code);

        smsMessageDTO.setContent(String.valueOf(code));

        // TODO: 2023/7/17 0017 暂时屏蔽，调试的时候放开
        // int status = smsService.send(smsMessageDTO);

        int status = 200;


        log.info("status is: {}", status);
        if(status == 200){
            // 返回成果: 验证码10分钟有效
            stringRedisTemplate.opsForValue().set(RedisKeys.USER_VALIDATE_CODE+smsMessageDTO.getMobile(), smsMessageDTO.getContent(), 600, TimeUnit.SECONDS);
        }

        return String.valueOf(status);
    }

    @Override
    public UserResponse loginAdmin(LoginRequest loginRequest) throws BizException {
        log.info(loginRequest.toString());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        UserResponse userResponse = loginService.login(loginRequest);
        if(userResponse == null){
            throw new BizException(UserErrorCode.USER_IS_NOT_ALLOW_LOGIN);
        }

        if(!userResponse.getPassword().equals(loginRequest.getPassword())){
            throw new BizException(UserErrorCode.LOGIN_PASSWORD_ERROR);
        }

        // 查看用户角色
        List<RolePO> roles = userRoleService.getRoleByUserId(userResponse.getId());
        if(roles == null && roles.size() <1){
            throw new BizException(UserErrorCode.USER_IS_NOT_ALLOW_LOGIN);
        }

        userResponse.setRoles(roles);

        String token = null;
        if(StrUtil.isNotBlank(userResponse.getToken())){
            token = userResponse.getToken();
        }else{
            token = UUID.randomUUID().toString().replace("-", "");
        }

        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().setHeader("token", token);
        userResponse.setToken(token);
        // token放到redis中
        stringRedisTemplate.opsForValue().set(RedisKeys.USER_TOKEN+token, String.valueOf(userResponse.getId()), Integer.valueOf(expireTime), TimeUnit.SECONDS);
        // 存储用户信息
        stringRedisTemplate.opsForValue().set(RedisKeys.USER_INFO+userResponse.getId(), JSONObject.toJSONString(userResponse), Integer.valueOf(expireTime), TimeUnit.SECONDS);

        return userResponse;
    }


    @Override
    public UserResponse login(LoginRequest loginRequest) throws BizException {
        log.info(loginRequest.toString());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        UserResponse userResponse = loginService.login(loginRequest);
        if(userResponse == null){
            throw new BizException(UserErrorCode.LOGIN_CODE_ERROR);
        }

        if(!userResponse.getPassword().equals(loginRequest.getPassword())){
            throw new BizException(UserErrorCode.LOGIN_PASSWORD_ERROR);
        }

        // 查看用户角色
        List<RolePO> roles = userRoleService.getRoleByUserId(userResponse.getId());
        userResponse.setRoles(roles);

        String token = null;
        if(StrUtil.isNotBlank(userResponse.getToken())){
            token = userResponse.getToken();
        }else{
            token = UUID.randomUUID().toString().replace("-", "");
        }

        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().setHeader("token", token);
        userResponse.setToken(token);
        // token放到redis中
        stringRedisTemplate.opsForValue().set(RedisKeys.USER_TOKEN+token, String.valueOf(userResponse.getId()), Integer.valueOf(expireTime), TimeUnit.SECONDS);
        // 存储用户信息
        stringRedisTemplate.opsForValue().set(RedisKeys.USER_INFO+userResponse.getId(), JSONObject.toJSONString(userResponse), Integer.valueOf(expireTime), TimeUnit.SECONDS);

        return userResponse;
    }

    // 四位随机码
    private long rand(){
        long num = Math.round(Math.random()* 10000) ;
        return num;
    }



}
