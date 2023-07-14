package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.IndexApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "indexApi", configuration = {KeepErrMsgConfiguration.class})
public interface IndexFeignApi extends IndexApi {
}
