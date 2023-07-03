package com.gxa.jbgsw.business;

import com.gxa.jbgsw.common.utils.RedisUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableSwagger2
@EnableDiscoveryClient
@MapperScan("com.gxa.jbgsw.business.mapper")
@EnableFeignClients
@EnableAsync
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    @Bean
    public MapperFacade getMapper() {
        DefaultMapperFactory mapperFactory = (new DefaultMapperFactory.Builder()).build();
        return mapperFactory.getMapperFacade();
    }

    @Bean
    public RedisUtils getRedisUtils(){
        return new RedisUtils();
    }


}
