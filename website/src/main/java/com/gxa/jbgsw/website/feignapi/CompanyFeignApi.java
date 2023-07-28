package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.CompanyApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "companyApi", configuration = {KeepErrMsgConfiguration.class})
public interface CompanyFeignApi extends CompanyApi {
}
