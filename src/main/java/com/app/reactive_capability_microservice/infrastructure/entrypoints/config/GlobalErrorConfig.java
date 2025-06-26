package com.app.reactive_capability_microservice.infrastructure.entrypoints.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalErrorConfig {
    @Bean
    public WebProperties.Resources webResources() {
        return new WebProperties.Resources();
    }
}
