package com.gxa.jbgsw.business.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
// 是否开启swagger，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），可根据springboot的多环境配置进行设置
//@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gxa.jbgsw.business.controller"))
                //过滤生成链接
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }


    /**
     * the api info
     *
     * @return api info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .license("Apache License Version 2.0")
                .title("admin")
                .description("api docs")
                .version("1.0")
                .build();
    }

}