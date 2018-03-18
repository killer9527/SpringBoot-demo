package com.wfw.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wfw.interceptor.LogInterceptor;
import com.wfw.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by killer9527 on 2018/3/5.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //LogInterceptor拦截器应用于所有URLs
        registry.addInterceptor(new LogInterceptor());

        //登录拦截器，不拦截登录接口和swagger静态资源
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("/user/login",
                        "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs");

        //addPathPatterns和excludePathPatterns分别用于指定路由和排除路由
    }
}
