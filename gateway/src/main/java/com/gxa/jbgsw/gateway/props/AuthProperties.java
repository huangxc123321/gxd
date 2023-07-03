package com.gxa.jbgsw.gateway.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;


// 权限过滤
@Data
@RefreshScope
// @ConfigurationProperties("skip.url")
@ConfigurationProperties(prefix = "skip")
public class AuthProperties {

    /**
     * 放行API集合
     */
    private final List<String> url = new ArrayList<>();

}