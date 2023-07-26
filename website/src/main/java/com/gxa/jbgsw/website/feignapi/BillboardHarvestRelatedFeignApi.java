package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.BillboardHarvestRelatedApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardHarvestRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardHarvestRelatedFeignApi extends BillboardHarvestRelatedApi {
}
