package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.AttentionApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "attentionApi", configuration = {KeepErrMsgConfiguration.class})
public interface AttentionFeignApi extends AttentionApi {
}
