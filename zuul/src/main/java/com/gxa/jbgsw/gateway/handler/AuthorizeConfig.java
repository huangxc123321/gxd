package com.gxa.jbgsw.gateway.handler;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
@ConfigurationProperties(prefix = "gdx.gateway.authorize")
public class AuthorizeConfig {

    private List<String> paths = new ArrayList<>();

    private List<String> optionalPaths = new ArrayList<>();

    public List<String> getPaths() {
        return paths;
    }

    public List<String> getOptionalPaths() {
        return optionalPaths;
    }
}
