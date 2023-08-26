package com.gxa.jbgsw.user.feignapi;

import com.gxa.jbgsw.business.client.CompanyApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "companyApi")
public interface CompanyFeignApi extends CompanyApi {
}
