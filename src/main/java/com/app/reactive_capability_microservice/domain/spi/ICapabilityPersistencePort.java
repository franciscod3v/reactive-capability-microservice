package com.app.reactive_capability_microservice.domain.spi;

import com.app.reactive_capability_microservice.domain.model.Capability;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapabilityPersistencePort {
    Mono<Capability> createCapability(Capability capability);
    Mono<Capability> findByName(String name);
    Mono<List<Capability>> getCapabilitiesByBootcampId(Long bootcampId);
}
