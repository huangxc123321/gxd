package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardLifecycleApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardLifecycleApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardLifecycleFeignApi extends BillboardLifecycleApi {
}
