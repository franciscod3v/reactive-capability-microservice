package com.app.reactive_capability_microservice.domain.spi;

import com.app.reactive_capability_microservice.domain.model.Capability;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapabilityPersistencePort {
    Mono<Capability> createCapability(Capability capability);
    Mono<Capability> findByName(String name);
}
