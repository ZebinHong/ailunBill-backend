package com.hunfeng.money.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket devApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .host("localhost:8081")
                .groupName("dev")
                .apiInfo(devApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(PathSelectors.any())
                .build();

    }
    private ApiInfo devApiInfo(){
        return new ApiInfoBuilder()
                .title("开发环境-API文档")
                .description("本文档描述了后台管理系统微服务接口定义")
                .version("1.0")
                .contact(new Contact("", "", ""))
                .build();
    }

    @Bean
    public Docket prodApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .host("changea.top")
                .protocols(Sets.newHashSet("http", "https"))
                .groupName("prod")
                .apiInfo(prodApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(PathSelectors.any())
                .build();

    }
    private ApiInfo prodApiInfo(){
        return new ApiInfoBuilder()
                .title("生产环境-API文档")
                .description("本文档描述了后台管理系统微服务接口定义")
                .version("1.0")
                .contact(new Contact("", "", ""))
                .build();
    }
}