package com.app.reactive_capability_microservice.domain.usecase;

import com.app.reactive_capability_microservice.domain.api.IGetAllCapacitiesServicePort;
import com.app.reactive_capability_microservice.domain.model.CapabilityWithTechnologies;
import com.app.reactive_capability_microservice.domain.spi.IGetAllCapabilitiesPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.ITechnologyGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllCapabilitiesUseCase implements IGetAllCapacitiesServicePort {

    private final IGetAllCapabilitiesPersistencePort capabilityPersistence;
    private final ITechnologyGateway technologyGateway;

    @Override
    public Flux<CapabilityWithTechnologies> getAllCapabilities(String sortBy, Boolean asc, Integer numberPage, Integer sizePage) {
        return capabilityPersistence.getAllCapabilities(sortBy, asc, numberPage, sizePage)
                .concatMap(capability ->
                        technologyGateway.getTechnologiesByCapabilityId(capability.getId())
                                .map(technologies -> new CapabilityWithTechnologies(
                                        capability.getId(),
                                        capability.getName(),
                                        capability.getDescription(),
                                        technologies
                                ))
                );
    }

}
