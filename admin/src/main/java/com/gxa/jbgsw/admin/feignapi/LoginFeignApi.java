package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.user.client.LoginApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-center-service", contextId = "loginApi", configuration = {KeepErrMsgConfiguration.class})
public interface LoginFeignApi extends LoginApi {
}
