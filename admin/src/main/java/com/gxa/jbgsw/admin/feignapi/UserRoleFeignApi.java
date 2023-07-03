package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.user.client.UserRoleApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-center-service", contextId = "userRoleFeignApi", configuration = {KeepErrMsgConfiguration.class})
public interface UserRoleFeignApi extends UserRoleApi {
}
