package com.gxa.jbgsw.website.feignapi;


import com.gxa.jbgsw.user.client.UserApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-center-service", contextId = "userApi", configuration = {KeepErrMsgConfiguration.class})
public interface UserFeignApi extends UserApi {
}
