package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardTalentRelatedApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardTalentRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardTalentRelatedFeignApi extends BillboardTalentRelatedApi {
}
