package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.basis.client.ProvinceCityDistrictApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-center-service", contextId = "provinceCityDistrictApi", configuration = {KeepErrMsgConfiguration.class})
public interface ProvinceCityDistrictFeignApi extends ProvinceCityDistrictApi {
}
