package com.gxa.jbgsw.user.service;

import com.gxa.jbgsw.user.entity.Authentication;
import com.baomidou.mybatisplus.extension.service.IService;


public interface AuthenticationService extends IService<Authentication> {

    void deleteBatchIds(Long[] ids);

    Authentication getAuthInfoByUserId(Long userId);
}
