package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.basis.client.DictionaryApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "dictionaryApi", configuration = {KeepErrMsgConfiguration.class})
public interface DictionaryFeignApi extends DictionaryApi {
}
