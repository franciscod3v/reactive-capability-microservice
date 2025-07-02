package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.adapter;

import com.app.reactive_capability_microservice.domain.model.BootcampCapability;
import com.app.reactive_capability_microservice.domain.spi.IBootcampCapabilityPersistencePort;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper.BootcampCapabilityEntityMapper;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository.IBootcampCapabilityRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class BootcampCapabilityAdapter implements IBootcampCapabilityPersistencePort {

    private final IBootcampCapabilityRepository repository;
    private final BootcampCapabilityEntityMapper mapper;

    @Override
    public Flux<BootcampCapability> saveAllBootcampCapability(Flux<BootcampCapability> bootcampCapabilityFlux) {
        return bootcampCapabilityFlux
                .map(mapper::toEntity)
                .collectList()
                .flatMapMany(repository::saveAll)
                .map(mapper::toModel);
    }
}
