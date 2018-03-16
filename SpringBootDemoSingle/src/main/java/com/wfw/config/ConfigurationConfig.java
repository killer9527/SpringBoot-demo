package com.wfw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by killer9527 on 2018/3/13.
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:config/application-develop.properties"),
        @PropertySource("classpath:config/application-release.properties"),
        @PropertySource("classpath:config/application-online.properties")
})
public class ConfigurationConfig {
}
