package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardTemporaryApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "billboardTemporaryApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardTemporaryFeignApi extends BillboardTemporaryApi {
}
