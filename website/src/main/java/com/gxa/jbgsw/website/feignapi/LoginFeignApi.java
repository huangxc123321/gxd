package com.gxa.jbgsw.website.feignapi;


import com.gxa.jbgsw.user.client.LoginApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-center-service", contextId = "loginApi", configuration = {KeepErrMsgConfiguration.class})
public interface LoginFeignApi extends LoginApi {
}
