package com.gxa.jbgsw.user.service;

import com.gxa.jbgsw.user.entity.Authentication;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 认证 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-25
 */
public interface AuthenticationService extends IService<Authentication> {

    void deleteBatchIds(Long[] ids);
}
