package com.app.reactive_capability_microservice.domain.api;

import com.app.reactive_capability_microservice.domain.model.BootcampCapability;
import reactor.core.publisher.Flux;

public interface IBootcampCapabilityServicePort {

    Flux<BootcampCapability> saveAllBootcampCapability(Flux<BootcampCapability> bootcampCapabilityFlux);

}
