package com.gxa.jbgsw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.entity.User;
import com.gxa.jbgsw.user.mapper.UserMapper;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.service.UserService;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public UserResponse getUserByCode(String code) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getMobile, code);
        lambdaQueryWrapper.last("limit 1");
        User user = userMapper.selectOne(lambdaQueryWrapper);

        UserResponse userResponse = null;
        if(user != null){
            userResponse = mapperFacade.map(user, UserResponse.class);
        }

        return userResponse;
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        userMapper.deleteBatchIds(list);
    }

    @Override
    public PageResult<UserResponse> pageQuery(UserRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<UserResponse> responses = userMapper.pageQuery(request);
        PageInfo<UserResponse> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<UserResponse>>() {
        }.build(), new TypeBuilder<PageResult<UserResponse>>() {}.build());
    }

    @Override
    public void add(User user) {
        this.save(user);
    }

    @Override
    public void updateUseStatus(Long id, Integer useStauts) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(User::getUseStauts, useStauts)
                .eq(User::getId, id);

        userMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        User user = this.getById(userDTO.getId());

        // 判断头像是否一致
        if((userDTO.getAvatar()!= null && !userDTO.getAvatar().equals(user.getAvatar())) || userDTO.getAvatar() == null ){
            user.setAvatar(userDTO.getAvatar());
        }
        // 判断昵称是否一致
        if((userDTO.getNick() != null && !userDTO.getNick().equals(user.getNick()))  || userDTO.getNick() == null ){
            user.setNick(userDTO.getNick());
        }

        // 判断地区是否一致
        if((userDTO.getAreaId() != null && !userDTO.getAreaId().equals(user.getAreaId())) || userDTO.getAreaId() == null ){
            user.setAreaId(userDTO.getAreaId());
            user.setAreaName(userDTO.getAreaName());
            user.setCityId(userDTO.getCityId());
            user.setCityName(userDTO.getCityName());
            user.setProvinceId(userDTO.getProvinceId());
            user.setProvinceName(userDTO.getProvinceName());
        }

        // 判断性别是否一致
        if((userDTO.getSex() != null && !userDTO.getSex().equals(user.getSex())) || userDTO.getSex() == null){
            user.setSex(userDTO.getSex());
        }
        user.setUpdateBy(userDTO.getUpdateBy());
        user.setUpdateAt(new Date());

        this.updateById(user);
    }

}
