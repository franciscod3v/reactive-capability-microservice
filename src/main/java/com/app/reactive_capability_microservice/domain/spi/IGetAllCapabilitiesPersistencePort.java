package com.app.reactive_capability_microservice.domain.spi;

import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.domain.model.CapabilityWithTechnologies;
import reactor.core.publisher.Flux;

public interface IGetAllCapabilitiesPersistencePort {
    Flux<Capability> getAllCapabilities(
            String sortBy,
            Boolean asc,
            Integer numberPage,
            Integer sizePage
    );
}
