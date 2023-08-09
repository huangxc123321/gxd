package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.MessageApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "messageApi", configuration = {KeepErrMsgConfiguration.class})
public interface MessageFeignApi extends MessageApi {
}
