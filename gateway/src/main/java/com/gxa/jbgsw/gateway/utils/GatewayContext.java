package com.gxa.jbgsw.gateway.utils;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 网关上下文
 */
@Data
public class GatewayContext {

    public static final String CACHE_GATEWAY_CONTEXT = "cacheGatewayContext";

    /**
     * cache headers
     */
    private HttpHeaders headers;

    /**
     * cache json body
     */
    private String cacheBody;
    /**
     * cache formdata
     */
    private MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

    /**
     * ipAddress
     */
    private String  ipAddress;

    /**
     * path
     */
    private String path;

}
