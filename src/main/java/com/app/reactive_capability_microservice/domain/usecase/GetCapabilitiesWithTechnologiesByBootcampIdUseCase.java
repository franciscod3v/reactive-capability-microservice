package com.app.reactive_capability_microservice.domain.usecase;

import com.app.reactive_capability_microservice.domain.api.IGetCapabilitiesWithTechnologiesByBootcampIdServicePort;
import com.app.reactive_capability_microservice.domain.model.CapabilityWithTechnologies;
import com.app.reactive_capability_microservice.domain.spi.ICapabilityPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.ITechnologyGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class GetCapabilitiesWithTechnologiesByBootcampIdUseCase implements IGetCapabilitiesWithTechnologiesByBootcampIdServicePort {

    private final ICapabilityPersistencePort adapter;
    private final ITechnologyGateway gateway;

    @Override
    public Mono<List<CapabilityWithTechnologies>> getCapabilitiesWithTechnologiesByBootcampId(Long bootcampId) {
        return adapter
                .getCapabilitiesByBootcampId(bootcampId)
                .flatMapMany(Flux::fromIterable)
                .flatMap(capability ->
                    gateway.getTechnologiesByCapabilityId(capability.getId())
                            .map(techs -> new CapabilityWithTechnologies(
                                    capability.getId(),
                                    capability.getName(),
                                    capability.getDescription(),
                                    techs
                            ))
                )
                .collectList();
    }
}
