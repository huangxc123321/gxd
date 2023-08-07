package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.basis.client.DictionaryTypeApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "dictionaryTypeApi" , configuration = {KeepErrMsgConfiguration.class})
public interface DictionaryTypeFeignApi  extends DictionaryTypeApi {
}
