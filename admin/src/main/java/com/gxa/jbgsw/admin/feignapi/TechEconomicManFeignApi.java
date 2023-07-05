package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.TechEconomicManApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author huangxc
 */
@FeignClient(name = "business-center-service", contextId = "techEconomicManApi", configuration = {KeepErrMsgConfiguration.class})
public interface TechEconomicManFeignApi extends TechEconomicManApi {
}
