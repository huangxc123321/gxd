package com.gxa.jbgsw.basis;


import com.gxa.jbgsw.common.utils.RedisUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.gxa.jbgsw.basis.mapper")
@EnableSwagger2
@EnableFeignClients
@EnableRedisHttpSession(flushMode = FlushMode.IMMEDIATE)
public class BasisCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasisCenterApplication.class, args);
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
