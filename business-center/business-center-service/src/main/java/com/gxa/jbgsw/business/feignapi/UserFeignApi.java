package com.gxa.jbgsw.business.feignapi;

import com.gxa.jbgsw.user.client.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 14:01
 * @Version 2.0
 */

@FeignClient(name = "user-center-client", contextId = "userApi")
public interface UserFeignApi extends UserApi {
}
