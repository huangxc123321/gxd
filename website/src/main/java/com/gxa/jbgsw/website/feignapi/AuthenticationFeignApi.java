package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.user.client.AuthenticationApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-center-service", contextId = "authenticationFeignApi", configuration = {KeepErrMsgConfiguration.class})
public interface AuthenticationFeignApi extends AuthenticationApi {
}
