package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardTemporaryApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "billboardTemporaryApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardTemporaryFeignApi extends BillboardTemporaryApi {
}
