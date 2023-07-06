package com.gxa.jbgsw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxa.jbgsw.user.entity.Authentication;
import com.gxa.jbgsw.user.mapper.AuthenticationMapper;
import com.gxa.jbgsw.user.service.AuthenticationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 认证 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-25
 */
@Service
public class AuthenticationServiceImpl extends ServiceImpl<AuthenticationMapper, Authentication> implements AuthenticationService {
    @Resource
    AuthenticationMapper authenticationMapper;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        authenticationMapper.deleteBatchIds(list);
    }

    @Override
    public Authentication getAuthInfoByUserId(Long userId) {
        LambdaQueryWrapper<Authentication> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Authentication::getUserId, userId);

        List<Authentication> authentications = authenticationMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(authentications)){
            return authentications.get(0);
        }

        return null;
    }
}
