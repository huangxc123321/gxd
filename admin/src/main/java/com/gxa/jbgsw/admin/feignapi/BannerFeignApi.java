package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.basis.client.BannerApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "bannerApi", configuration = {KeepErrMsgConfiguration.class})
public interface BannerFeignApi extends BannerApi {
}
