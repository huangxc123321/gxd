
package com.gxa.jbgsw.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.math.BigDecimal;
import java.math.BigInteger;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     * 初始化对象
     * @param builder
     */
    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        final JavaTimeModule simpleModule = new JavaTimeModule();
        //添加Long类型
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        SimpleModule bigIntegerModule = new SimpleModule();
        //添加BigInteger类型
        bigIntegerModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        SimpleModule bigDecimalModule = new SimpleModule();
        //添加BigDecimal类型
        bigDecimalModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        return builder.createXmlMapper(false).modulesToInstall(simpleModule).build();
    }


}
