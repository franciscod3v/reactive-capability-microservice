package com.app.reactive_capability_microservice.application.config;

import com.app.reactive_capability_microservice.domain.api.IBootcampCapabilityServicePort;
import com.app.reactive_capability_microservice.domain.api.ICapabilityServicePort;
import com.app.reactive_capability_microservice.domain.api.IGetAllCapacitiesServicePort;
import com.app.reactive_capability_microservice.domain.spi.IBootcampCapabilityPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.ICapabilityPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.IGetAllCapabilitiesPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.ITechnologyGateway;
import com.app.reactive_capability_microservice.domain.usecase.CreateBootcampCapabilityUseCase;
import com.app.reactive_capability_microservice.domain.usecase.CreateCapabilityUseCase;
import com.app.reactive_capability_microservice.domain.usecase.GetAllCapabilitiesUseCase;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.adapter.BootcampCapabilityAdapter;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.adapter.CapabilityAdapter;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper.BootcampCapabilityEntityMapper;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper.CapabilityEntityMapper;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository.CapabilityCustomRepository;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository.IBootcampCapabilityRepository;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository.ICapabilityRepository;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.technologyservice.adapter.TechnologyWebClientAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.r2dbc.core.DatabaseClient;
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
    public CapabilityCustomRepository capabilityCustomRepository(DatabaseClient databaseClient) {
        return new CapabilityCustomRepository(databaseClient);
    }

    @Bean
    public CapabilityAdapter capabilityAdapter(
            ICapabilityRepository repository,
            CapabilityEntityMapper mapper,
            CapabilityCustomRepository customRepository
    ) {
        return new CapabilityAdapter(repository, mapper, customRepository);
    }

    @Bean
    public ICapabilityPersistencePort capabilityPersistencePort(CapabilityAdapter adapter) {
        return adapter;
    }

    @Bean
    public IGetAllCapabilitiesPersistencePort getAllCapabilitiesPersistencePort(CapabilityAdapter adapter) {
        return adapter;
    }

    @Bean
    public ITechnologyGateway technologyGateway(WebClient webClient) {
        return new TechnologyWebClientAdapter(webClient);
    }

    @Bean
    public ICapabilityServicePort capabilityServicePort(ICapabilityPersistencePort capabilityPersistencePort) {
        return new CreateCapabilityUseCase(capabilityPersistencePort);
    }

    @Bean
    public IGetAllCapacitiesServicePort getAllCapacitiesServicePort(
            IGetAllCapabilitiesPersistencePort getAllCapabilitiesPersistencePort,
            ITechnologyGateway technologyGateway
    ) {
        return new GetAllCapabilitiesUseCase(getAllCapabilitiesPersistencePort, technologyGateway);
    }

    @Bean
    public IBootcampCapabilityPersistencePort bootcampCapabilityPersistencePort(
            IBootcampCapabilityRepository repository,
            BootcampCapabilityEntityMapper mapper
    ) {
        return new BootcampCapabilityAdapter(repository, mapper);
    }

    @Bean
    public IBootcampCapabilityServicePort bootcampCapabilityServicePort(
            IBootcampCapabilityPersistencePort bootcampCapabilityPersistencePort
    ) {
        return new CreateBootcampCapabilityUseCase(bootcampCapabilityPersistencePort);
    }
}
