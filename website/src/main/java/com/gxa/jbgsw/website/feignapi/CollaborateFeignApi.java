package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.CollaborateApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "collaborateApi", configuration = {KeepErrMsgConfiguration.class})
public interface CollaborateFeignApi extends CollaborateApi {
}
