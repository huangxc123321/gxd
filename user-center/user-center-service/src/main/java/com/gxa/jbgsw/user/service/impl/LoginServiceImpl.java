package com.gxa.jbgsw.user.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.LoginRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.enums.LoginWayEnum;
import com.gxa.jbgsw.user.service.LoginService;
import com.gxa.jbgsw.user.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    UserService userService;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public UserResponse login(LoginRequest loginRequest) {

        UserResponse userResponse = null;
        // 0 账号+密码登录
        if(LoginWayEnum.ACCOUNT_PASSWORD.getCode().equals(loginRequest.getLoginWay())){
            userResponse = userService.getUserByCode(loginRequest.getMobile());
        }
        // 1 账号+验证码
        else if(LoginWayEnum.ACCOUNT_VALIDATECODE.getCode().equals(loginRequest.getLoginWay())){
            userResponse = userService.getUserByValidateCode(loginRequest.getMobile(), loginRequest.getValidateCode());
        }
        // 2 扫描二维码登录
        else if(LoginWayEnum.SCAN_QR_TOKEN.getCode().equals(loginRequest.getLoginWay())){
            String redisKeys = RedisKeys.USER_TOKEN+loginRequest.getToken();
            String userId = stringRedisTemplate.opsForValue().get(redisKeys);
            if(StrUtil.isNotBlank(userId)){
                String userInfo = stringRedisTemplate.opsForValue().get(RedisKeys.USER_INFO+userId);

                if(StrUtil.isNotBlank(userInfo)){
                    userResponse = JSONObject.parseObject(userInfo, UserResponse.class);
                }
            }
        }

        return userResponse;
    }
}
