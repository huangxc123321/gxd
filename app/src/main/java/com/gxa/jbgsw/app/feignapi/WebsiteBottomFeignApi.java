package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.basis.client.WebsiteBottomApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "websiteBottomApi", configuration = {KeepErrMsgConfiguration.class})
public interface WebsiteBottomFeignApi extends WebsiteBottomApi {
}
