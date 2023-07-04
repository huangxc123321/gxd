package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.basis.client.WebsiteBottomApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "websiteBottomApi", configuration = {KeepErrMsgConfiguration.class})
public interface WebsiteBottomFeignApi extends WebsiteBottomApi {
}
