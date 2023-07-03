package com.gxa.jbgsw.admin.feignapi;


import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.user.client.RoleMenuApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-center-service", contextId = "roleMenuFeignApi", configuration = {KeepErrMsgConfiguration.class})
public interface RoleMenuFeignApi extends RoleMenuApi {
}
