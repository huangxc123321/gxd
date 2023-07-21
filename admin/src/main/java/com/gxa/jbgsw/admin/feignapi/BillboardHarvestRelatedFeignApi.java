package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardHarvestRelatedApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardHarvestRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardHarvestRelatedFeignApi extends BillboardHarvestRelatedApi {
}
