package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.user.client.AuthenticationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-center-service", contextId = "authenticationApi", configuration = {KeepErrMsgConfiguration.class})
public interface AuthenticationFeignApi extends AuthenticationApi {
}
