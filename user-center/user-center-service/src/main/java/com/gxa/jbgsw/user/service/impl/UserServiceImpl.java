package com.gxa.jbgsw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.protocol.dto.CompanyDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.entity.User;
import com.gxa.jbgsw.user.feignapi.CompanyFeignApi;
import com.gxa.jbgsw.user.mapper.UserMapper;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.enums.UserTypeEnum;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.user.service.UserService;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    CompanyFeignApi companyFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;
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
    @Transactional
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        userMapper.deleteBatchIds(list);

        for(int i=0; i<list.size(); i++){
            Long id = list.get(i);

            String key = RedisKeys.USER_INFO+id;
            stringRedisTemplate.delete(key);
        }
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
    @Transactional
    public void add(User user) {
        this.save(user);


        // 如果是企业类型的还需要生成一个企业数据
        if(user.getUnitNature().equals(UserTypeEnum.GOV.getCode())
                || user.getUnitNature().equals(UserTypeEnum.BUZ.getCode())
                || user.getUnitNature().equals(UserTypeEnum.TEAM.getCode())
                || user.getUnitNature().equals(UserTypeEnum.EDU.getCode())){
            CompanyDTO companyDTO = new CompanyDTO();
            CompanyDTO existsCompany = companyFeignApi.getCompanyByUnitName(user.getUnitName());
            if(existsCompany == null){
                companyDTO.setName(user.getUnitName());
                companyDTO.setLogo(user.getUnitLogo());
                companyDTO.setProvinceId(user.getProvinceId());
                companyDTO.setProvinceName(user.getProvinceName());
                companyDTO.setCityName(user.getCityName());
                companyDTO.setCityId(user.getCityId());
                companyDTO.setAreaId(user.getAreaId());
                companyDTO.setAreaName(user.getAreaName());
                companyDTO.setTradeType(String.valueOf(user.getTradeType()));
                companyDTO.setCreateAt(new Date());
                companyDTO.setCreateBy(user.getCreateBy());
                companyDTO.setDirector(user.getNick());
                companyDTO.setRemark(user.getRemark());
                companyDTO.setStatus(0);
                companyDTO.setScopeBusiness(user.getScopeBusiness());
                companyDTO.setMobile(user.getMobile());

                companyFeignApi.add(companyDTO);
            }
        }
    }

    @Override
    @Transactional
    public void updateUseStatus(Long id, Integer useStauts) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(User::getUseStauts, useStauts)
                .eq(User::getId, id);

        userMapper.update(null, lambdaUpdateWrapper);
    }


    @Override
    @Transactional
    public void updateUserAdmin(UserDTO userDTO) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        User user = this.getById(userDTO.getId());

        // 判断电话是否一致
        if(user.getMobile() != null && !userDTO.getMobile().equals(user.getMobile())){
            // 判断手机号码是否注册
            UserRequest userRequest = new UserRequest();
            userRequest.setSearchFiled(userDTO.getMobile());
            PageResult<UserResponse> pageResult = this.pageQuery(userRequest);
            if(pageResult.getTotal()>0){
                throw new BizException(UserErrorCode.USER_PHONE_IS_EXISTS);
            }

            user.setMobile(userDTO.getMobile());
        }

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
        user.setTradeType(userDTO.getTradeType());
        user.setUnitNature(userDTO.getUnitNature());
        user.setJob(userDTO.getJob());
        user.setUnitName(userDTO.getUnitName());
        user.setUnitLogo(userDTO.getUnitLogo());
        user.setRemark(userDTO.getRemark());
        user.setBuzType(userDTO.getBuzType());
        user.setScopeBusiness(userDTO.getScopeBusiness());
        user.setTechDomain(userDTO.getTechDomain());
        user.setTechDomain1(userDTO.getTechDomain1());
        user.setTechDomain2(userDTO.getTechDomain2());

        this.updateById(user);
    }



    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        User user = this.getById(userDTO.getId());

        // 判断电话是否一致
        if(user.getMobile() != null && !userDTO.getMobile().equals(user.getMobile())){
            // 判断验证码是否过期
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
            UserRequest userRequest = new UserRequest();
            userRequest.setSearchFiled(userDTO.getMobile());
            PageResult<UserResponse> pageResult = this.pageQuery(userRequest);
            if(pageResult.getTotal()>0){
                throw new BizException(UserErrorCode.USER_PHONE_IS_EXISTS);
            }

            user.setMobile(userDTO.getMobile());
        }

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
        user.setTradeType(userDTO.getTradeType());
        user.setUnitNature(userDTO.getUnitNature());
        user.setJob(userDTO.getJob());
        user.setBuzType(userDTO.getBuzType());
        user.setScopeBusiness(userDTO.getScopeBusiness());
        user.setRemark(userDTO.getRemark());
        user.setUnitLogo(userDTO.getUnitLogo());
        user.setUnitName(userDTO.getUnitName());
        user.setTechDomain(userDTO.getTechDomain());
        user.setTechDomain1(userDTO.getTechDomain1());
        user.setTechDomain2(userDTO.getTechDomain2());

        this.updateById(user);
    }

    @Override
    public UserResponse getUserByValidateCode(String mobile, String validateCode) {
        // 从redis中获取验证码,对比
        String key = RedisKeys.USER_VALIDATE_CODE+mobile;
        Object value = stringRedisTemplate.opsForValue().get(key);
        // 验证码为空或者错误，返回错误提示
        if(value == null || !value.toString().equals(validateCode)){
            throw new BizException(UserErrorCode.LOGIN_VALIDATECODE_IS_ERROR);
        }
        // 通过手机号获取用户信息
        UserResponse userResponse = this.getUserByCode(mobile);

        return userResponse;
    }

    @Override
    @Transactional
    public void updatePassword(String mobile, String password) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(User::getPassword, password)
                .eq(User::getMobile, mobile);

        userMapper.update(null, lambdaUpdateWrapper);
    }

}
