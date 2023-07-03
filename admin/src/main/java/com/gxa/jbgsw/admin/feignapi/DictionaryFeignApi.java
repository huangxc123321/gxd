package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.basis.client.DictionaryApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "dictionaryApi", configuration = {KeepErrMsgConfiguration.class})
public interface DictionaryFeignApi extends DictionaryApi {
}
