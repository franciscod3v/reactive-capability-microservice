package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.adapter;

import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.domain.spi.ICapabilityPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.IGetAllCapabilitiesPersistencePort;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper.CapabilityEntityMapper;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository.CapabilityCustomRepository;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository.ICapabilityRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CapabilityAdapter implements ICapabilityPersistencePort, IGetAllCapabilitiesPersistencePort {

    private final ICapabilityRepository repository;
    private final CapabilityEntityMapper mapper;
    private final CapabilityCustomRepository customRepository;

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

    @Override
    public Flux<Capability> getAllCapabilities(String sortBy, Boolean asc, Integer numberPage, Integer sizePage) {
        return customRepository
                .findPaginatedAndSorted(sortBy, asc, numberPage, sizePage)
                .map(mapper::toDomain);
    }
}
