package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.business.client.TechEconomicManAppraiseApi;
import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "techEconomicManAppraiseApi", configuration = {KeepErrMsgConfiguration.class})
public interface TechEconomicManAppraiseFeignApi extends TechEconomicManAppraiseApi {
}
