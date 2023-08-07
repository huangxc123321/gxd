package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.basis.client.DictionaryApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "dictionaryApi", configuration = {KeepErrMsgConfiguration.class})
public interface DictionaryFeignApi extends DictionaryApi {
}
