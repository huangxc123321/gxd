package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.BillboardGainApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author huangxc
 */
@FeignClient(name = "business-center-service", contextId = "billboardGainApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardGainFeignApi extends BillboardGainApi {
}
