package com.app.reactive_capability_microservice.domain.api;

import com.app.reactive_capability_microservice.domain.model.Capability;
import reactor.core.publisher.Mono;

public interface ICapabilityServicePort {
    Mono<Capability> createCapability(Capability capability);
}
