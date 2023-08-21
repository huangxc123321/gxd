package com.gxa.jbgsw.business.feignapi;

import com.gxa.jbgsw.basis.client.ProvinceCityDistrictApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "provinceCityDistrictApi")
public interface ProvinceCityDistrictFeignApi extends ProvinceCityDistrictApi {
}
