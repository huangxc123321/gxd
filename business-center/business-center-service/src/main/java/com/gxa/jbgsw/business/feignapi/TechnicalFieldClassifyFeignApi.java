package com.gxa.jbgsw.business.feignapi;

import com.gxa.jbgsw.basis.client.TechnicalFieldClassifyApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author Mr. huang
 * @Date 2023/8/2 0002 13:43
 * @Version 2.0
 */
@FeignClient(name = "basis-center-service", contextId = "technicalFieldClassifyApi")
public interface TechnicalFieldClassifyFeignApi extends TechnicalFieldClassifyApi {
}
