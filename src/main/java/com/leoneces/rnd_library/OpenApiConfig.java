package com.leoneces.rnd_library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.util.ResourceUtils;

@Configuration
public class OpenApiConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/v3/api-docs.yaml")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/openapi.yaml");
    }
}
