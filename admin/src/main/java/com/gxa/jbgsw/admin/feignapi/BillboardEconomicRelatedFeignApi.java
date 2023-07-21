package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardEconomicRelatedApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardEconomicRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardEconomicRelatedFeignApi extends BillboardEconomicRelatedApi {
}
