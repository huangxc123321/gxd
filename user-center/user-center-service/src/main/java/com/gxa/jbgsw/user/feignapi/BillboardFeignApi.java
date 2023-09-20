package com.gxa.jbgsw.user.feignapi;


import com.gxa.jbgsw.business.client.BillboardApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "billboardApi")
public interface BillboardFeignApi extends BillboardApi {
}
