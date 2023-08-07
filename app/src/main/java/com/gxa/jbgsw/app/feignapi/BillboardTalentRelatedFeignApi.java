package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.BillboardTalentRelatedApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardTalentRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardTalentRelatedFeignApi extends BillboardTalentRelatedApi {
}
