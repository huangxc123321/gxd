package com.gxa.jbgsw.admin.feignapi;


import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.user.client.MenuApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-center-service", contextId = "menuApi" , configuration = {KeepErrMsgConfiguration.class})
public interface MenuFeignApi extends MenuApi {
}
