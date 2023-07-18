package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.TechEconomicManAppraiseApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "techEconomicManAppraiseApi", configuration = {KeepErrMsgConfiguration.class})
public interface TechEconomicManAppraiseFeignApi extends TechEconomicManAppraiseApi {
}
