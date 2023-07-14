package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.TechEconomicManApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "techEconomicManApi", configuration = {KeepErrMsgConfiguration.class})
public interface TechEconomicManFeignApi extends TechEconomicManApi {
}
