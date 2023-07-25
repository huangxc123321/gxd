package com.gxa.jbgsw.gateway;


import com.gxa.jbgsw.gateway.filter.ApiResultResponseFilter;
import com.gxa.jbgsw.gateway.filter.AuthorizedFilter;
import com.gxa.jbgsw.gateway.filter.ErrorResponseFilter;
import com.gxa.jbgsw.gateway.filter.TokenFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class  GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    //实现负载均衡的注解
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ApiResultResponseFilter getApiResultResponseFilter(){
        return new ApiResultResponseFilter();
    }

    @Bean
    public AuthorizedFilter getAuthorizedFilter(){
        return new AuthorizedFilter();
    }

    @Bean
    public ErrorResponseFilter getErrorResponseFilter() {
        return new ErrorResponseFilter();
    }

    @Bean
    public TokenFilter getTokenFilter(){
        return  new TokenFilter();
    }



    /**
     * 解决Jackson导致Long型数据精度丢失问题
     * @return
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(module);
        return objectMapper;
    }



    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        // 前后端分离，在后端可以取到 token 了
        // corsConfiguration.addExposedHeader("token");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

}
