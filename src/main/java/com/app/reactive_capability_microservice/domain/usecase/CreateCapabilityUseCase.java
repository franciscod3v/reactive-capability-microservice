package com.app.reactive_capability_microservice.domain.usecase;

import com.app.reactive_capability_microservice.domain.api.ICapabilityServicePort;
import com.app.reactive_capability_microservice.domain.enums.TechnicalMessage;
import com.app.reactive_capability_microservice.domain.exception.BusinessException;
import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.domain.spi.ICapabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateCapabilityUseCase implements ICapabilityServicePort {

    private final ICapabilityPersistencePort persistenceAdapter;

    @Override
    public Mono<Capability> createCapability(Capability capability) {
        return persistenceAdapter
                .findByName(capability.getName())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BusinessException(TechnicalMessage.CAPABILITY_ALREADY_EXISTS));
                    }
                    return persistenceAdapter.createCapability(capability);
                });
    }
}
