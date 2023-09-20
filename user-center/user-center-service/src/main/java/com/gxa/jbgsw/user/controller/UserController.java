package com.gxa.jbgsw.user.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxa.jbgsw.business.client.CompanyApi;
import com.gxa.jbgsw.business.protocol.dto.CompanyDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.client.UserApi;
import com.gxa.jbgsw.user.entity.User;
import com.gxa.jbgsw.user.feignapi.CompanyFeignApi;
import com.gxa.jbgsw.user.protocol.dto.*;
import com.gxa.jbgsw.user.protocol.enums.UserTypeEnum;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.user.service.RoleService;
import com.gxa.jbgsw.user.service.SmsService;
import com.gxa.jbgsw.user.service.UserRoleService;
import com.gxa.jbgsw.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

import org.apache.commons.collections4.CollectionUtils;
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
    SmsService smsService;
    @Resource
    UserRoleService userRoleService;

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
    public UserResponse getUserByCode(String code, Integer platform) {
        return userService.getUserByCode(code, platform);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        userService.deleteBatchIds(ids);
    }

    @Override
    public PageResult<UserResponse> pageQuery(UserRequest request) {
        PageResult<UserResponse> pageResult = userService.pageQuery(request);

        List<UserResponse> userResponses = pageResult.getList();
        if(CollectionUtils.isNotEmpty(userResponses)){
            userResponses.stream().forEach(s->{
                List<RolePO>  roles = userRoleService.getRoleByUserId(s.getId());
                s.setRoles(roles);
            });
        }


        return pageResult;
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

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(User::getId, ids);
        List<User> users = userService.list(lambdaQueryWrapper);
        return mapperFacade.mapAsList(users, UserResponse.class);
    }

    @Override
    public UserResponse getUserByMobile(String mobile) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getMobile, mobile);

        List<User> users = userService.list(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(users)){
            User user = users.get(0);
            UserResponse userDTO = mapperFacade.map(user, UserResponse.class);
            return userDTO;
        }
        return null;
    }

    @Override
    public void updateUserAdmin(UserDTO userDTO) {
        userService.updateUserAdmin(userDTO);
    }

    @Override
    public void sendSms() throws Exception {
        SmsMessageDTO smsMessage = new SmsMessageDTO();
        smsMessage.setMobile("18038109306");
        smsMessage.setType(1);
        smsMessage.setContent("测试短信");

        smsService.send(smsMessage);
    }

    @Override
    public void updateAdminPassword(UpdateAdminPasswordDTO updateAdminPasswordDTO) {
        userService.updateAdminPassword(updateAdminPasswordDTO);
    }
}
