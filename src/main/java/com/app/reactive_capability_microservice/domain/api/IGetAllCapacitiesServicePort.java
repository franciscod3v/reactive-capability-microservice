package com.app.reactive_capability_microservice.domain.api;

import com.app.reactive_capability_microservice.domain.model.CapabilityWithTechnologies;
import reactor.core.publisher.Flux;

public interface IGetAllCapacitiesServicePort {
    Flux<CapabilityWithTechnologies> getAllCapabilities(
            String sortBy,
            Boolean asc,
            Integer numberPage,
            Integer sizePage
    );
}
