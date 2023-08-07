package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.BillboardHarvestRelatedApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardHarvestRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardHarvestRelatedFeignApi extends BillboardHarvestRelatedApi {
}
