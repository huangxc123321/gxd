package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.CompanyApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-center-service", contextId = "companyApi", configuration = {KeepErrMsgConfiguration.class})
public interface CompanyFeignApi extends CompanyApi {
}
