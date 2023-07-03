package com.gxa.jbgsw.business.feignapi;


import com.gxa.jbgsw.basis.client.DictionaryApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "dictionaryApi")
public interface DictionaryFeignApi extends DictionaryApi {
}
