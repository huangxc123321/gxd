package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.basis.client.WebsiteBottomApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "websiteBottomApi", configuration = {KeepErrMsgConfiguration.class})
public interface WebsiteBottomFeignApi extends WebsiteBottomApi {
}
