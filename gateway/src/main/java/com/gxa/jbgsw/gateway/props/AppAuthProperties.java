package com.gxa.jbgsw.gateway.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

@Data
@RefreshScope
@ConfigurationProperties(prefix="appauth")
public class AppAuthProperties {
    private final List<String> url = new ArrayList<>();
}
