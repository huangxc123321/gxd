package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.CollaborateApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "collaborateApi", configuration = {KeepErrMsgConfiguration.class})
public interface CollaborateFeignApi extends CollaborateApi {
}
