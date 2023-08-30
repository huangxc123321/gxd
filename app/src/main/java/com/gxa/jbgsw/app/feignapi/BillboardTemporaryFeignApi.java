package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardTemporaryApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "billboardTemporaryApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardTemporaryFeignApi extends BillboardTemporaryApi {
}
