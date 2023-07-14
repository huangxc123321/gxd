package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.basis.client.BannerApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "bannerApi", configuration = {KeepErrMsgConfiguration.class})
public interface BannerFeignApi extends BannerApi {
}
