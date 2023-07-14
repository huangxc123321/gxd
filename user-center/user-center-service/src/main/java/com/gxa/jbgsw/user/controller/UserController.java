package com.gxa.jbgsw.user.controller;

import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.client.UserApi;
import com.gxa.jbgsw.user.entity.User;
import com.gxa.jbgsw.user.protocol.dto.UpdatePasswordDTO;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "用户管理")
@RestController
@Slf4j
public class UserController implements UserApi {
    @Resource
    UserService userService;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public UserResponse getUserById(Long id) {
        User user = userService.getById(id);
        UserResponse userResponse = mapperFacade.map(user, UserResponse.class);

        return userResponse;
    }

    @Override
    public UserResponse getUserByCode(String code) {
        return userService.getUserByCode(code);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        userService.deleteBatchIds(ids);
    }

    @Override
    public PageResult<UserResponse> pageQuery(UserRequest request) {
        return userService.pageQuery(request);
    }

    @Override
    public void add(UserDTO userDTO) {
        User user = mapperFacade.map(userDTO, User.class);
        user.setCreateAt(new Date());

        userService.add(user);
    }

    @Override
    public void updateUseStatus(Long id, Integer useStauts) {
        userService.updateUseStatus(id, useStauts);
    }

    @Override
    public void update(UserDTO userDTO) {
        userService.updateUser(userDTO);
    }

    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        // 先检测验证码是否正确, 从redis中获取验证码,对比
        String key = RedisKeys.USER_VALIDATE_CODE+updatePasswordDTO.getMobile();
        Object value = stringRedisTemplate.opsForValue().get(key);
        // 验证码为空或者错误，返回错误提示
        if(value == null || !value.toString().equals(updatePasswordDTO.getValidateCode())){
            throw new BizException(UserErrorCode.LOGIN_VALIDATECODE_IS_ERROR);
        }

        userService.updatePassword(updatePasswordDTO.getMobile(), updatePasswordDTO.getPassword());
    }

    @Override
    public List<UserResponse> getUserByIds(Long[] ids) {
        List<Long> userIds = Arrays.stream(ids).collect(Collectors.toList());

        List<User> users = userService.listByIds(userIds);
        return mapperFacade.mapAsList(users, UserResponse.class);
    }
}
