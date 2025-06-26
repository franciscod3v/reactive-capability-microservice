package com.app.reactive_capability_microservice.application.config;

import com.app.reactive_capability_microservice.domain.api.ICapabilityServicePort;
import com.app.reactive_capability_microservice.domain.spi.ICapabilityPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.ITechnologyGateway;
import com.app.reactive_capability_microservice.domain.usecase.CreateCapabilityUseCase;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.adapter.CapabilityAdapter;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper.CapabilityEntityMapper;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository.ICapabilityRepository;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.technologyservice.adapter.TechnologyWebClientAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    public WebClient webClient(@Value("${technology-service.base-url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public ICapabilityPersistencePort capabilityPersistencePort(ICapabilityRepository repository, CapabilityEntityMapper mapper) {
        return new CapabilityAdapter(repository, mapper);
    }

    @Bean
    public ITechnologyGateway technologyGateway(WebClient webClient) {
        return new TechnologyWebClientAdapter(webClient);
    }

    @Bean
    public ICapabilityServicePort capabilityServicePort(ICapabilityPersistencePort capabilityPersistencePort) {
        return new CreateCapabilityUseCase(capabilityPersistencePort);
    }
}
