package com.gxa.jbgsw.app.feignapi;


import com.gxa.jbgsw.business.client.BillboardApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "billboardApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardFeignApi extends BillboardApi {
}
