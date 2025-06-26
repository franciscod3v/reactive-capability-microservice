package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.adapter;

import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.domain.spi.ICapabilityPersistencePort;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper.CapabilityEntityMapper;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository.ICapabilityRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CapabilityAdapter implements ICapabilityPersistencePort {

    private final ICapabilityRepository repository;
    private final CapabilityEntityMapper mapper;

    @Override
    public Mono<Capability> createCapability(Capability capability) {
        return repository
                .save(mapper.toEntity(capability))
                .map(mapper::toDomain);

    }

    @Override
    public Mono<Capability> findByName(String name) {
        return repository
                .findByName(name)
                .map(mapper::toDomain);
    }
}
