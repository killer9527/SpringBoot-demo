package com.wfw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by killer9527 on 2018/3/5.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket restfulApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wfw.controller"))
                .build().globalOperationParameters(GetHeader())
                .apiInfo(initApiInfo());
    }

    private List<Parameter> GetHeader() {
        ParameterBuilder token = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        token.name("X-Request-Token").description("Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(token.build());
        return parameters;

    }

    private ApiInfo initApiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("SpringBoot Demo后端WebAPI")
                .description(initContextInfo())
                .version("1.0")
                .build();

        return apiInfo;
    }

    private String initContextInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("REST API 设计在细节上有很多自己独特的需要注意的技巧，并且对开发人员在构架设计能力上比传统 API 有着更高的要求。").append("<br/>")
                .append("以下是本项目的API文档");
        return sb.toString();
    }
}
