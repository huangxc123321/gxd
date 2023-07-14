package com.gxa.jbgsw.app.feignapi;


import com.gxa.jbgsw.user.client.LoginApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-center-service", contextId = "loginApi", configuration = {KeepErrMsgConfiguration.class})
public interface LoginFeignApi extends LoginApi {
}
