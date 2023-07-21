package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardTalentRelatedApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardTalentRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardTalentRelatedFeignApi extends BillboardTalentRelatedApi {
}
