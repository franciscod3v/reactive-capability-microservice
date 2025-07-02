package com.app.reactive_capability_microservice.domain.spi;

import com.app.reactive_capability_microservice.domain.model.BootcampCapability;
import reactor.core.publisher.Flux;

public interface IBootcampCapabilityPersistencePort {
    Flux<BootcampCapability> saveAllBootcampCapability(Flux<BootcampCapability> bootcampCapabilityFlux);
}
