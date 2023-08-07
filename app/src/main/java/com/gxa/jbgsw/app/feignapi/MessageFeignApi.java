package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.MessageApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "messageApi", configuration = {KeepErrMsgConfiguration.class})
public interface MessageFeignApi extends MessageApi {
}
