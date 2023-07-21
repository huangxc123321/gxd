package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.basis.client.ProvinceCityDistrictApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-center-service", contextId = "provinceCityDistrictApi", configuration = {KeepErrMsgConfiguration.class})
public interface ProvinceCityDistrictFeignApi extends ProvinceCityDistrictApi {
}
