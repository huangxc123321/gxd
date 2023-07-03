package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.user.client.RoleApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-center-service", contextId = "roleFeignApi", configuration = {KeepErrMsgConfiguration.class})
public interface RoleFeignApi extends RoleApi {
}
