package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.IndexApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "indexApi", configuration = {KeepErrMsgConfiguration.class})
public interface IndexFeignApi extends IndexApi {
}
