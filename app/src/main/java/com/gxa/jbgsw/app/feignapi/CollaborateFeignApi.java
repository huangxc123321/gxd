package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.CollaborateApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "collaborateApi", configuration = {KeepErrMsgConfiguration.class})
public interface CollaborateFeignApi extends CollaborateApi {
}
