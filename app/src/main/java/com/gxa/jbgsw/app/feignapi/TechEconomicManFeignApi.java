package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.TechEconomicManApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "techEconomicManApi", configuration = {KeepErrMsgConfiguration.class})
public interface TechEconomicManFeignApi extends TechEconomicManApi {
}
