package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.LastBillboardSetApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author huangxc
 */
@FeignClient(name = "business-center-service", contextId = "lastBillboardSetApi", configuration = {KeepErrMsgConfiguration.class})
public interface LastBillboardSetFeignApi extends LastBillboardSetApi {
}
