package com.gxa.jbgsw.admin.feignapi;



import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.user.client.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-center-service", contextId = "userApi", configuration = {KeepErrMsgConfiguration.class})
public interface UserFeignApi extends UserApi {
}
