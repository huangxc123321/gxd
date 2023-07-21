package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.basis.client.DictionaryTypeApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "dictionaryTypeApi" , configuration = {KeepErrMsgConfiguration.class})
public interface DictionaryTypeFeignApi  extends DictionaryTypeApi {
}
