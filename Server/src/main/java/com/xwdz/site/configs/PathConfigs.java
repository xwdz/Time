package com.xwdz.site.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class PathConfigs extends WebMvcConfigurerAdapter {

    @Value("${upload.path}")
    private String saveFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations(
                "file:" + saveFilePath);
        super.addResourceHandlers(registry);
    }
}
