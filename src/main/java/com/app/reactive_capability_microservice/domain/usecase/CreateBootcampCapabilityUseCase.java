package com.app.reactive_capability_microservice.domain.usecase;

import com.app.reactive_capability_microservice.domain.api.IBootcampCapabilityServicePort;
import com.app.reactive_capability_microservice.domain.model.BootcampCapability;
import com.app.reactive_capability_microservice.domain.spi.IBootcampCapabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class CreateBootcampCapabilityUseCase implements IBootcampCapabilityServicePort {

    private final IBootcampCapabilityPersistencePort adapter;

    @Override
    public Flux<BootcampCapability> saveAllBootcampCapability(Flux<BootcampCapability> bootcampCapabilityFlux) {
        return adapter.saveAllBootcampCapability(bootcampCapabilityFlux);
    }
}
