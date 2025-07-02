package com.app.reactive_capability_microservice.domain.api;

import com.app.reactive_capability_microservice.domain.model.CapabilityWithTechnologies;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IGetCapabilitiesWithTechnologiesByBootcampIdServicePort {
    Mono<List<CapabilityWithTechnologies>> getCapabilitiesWithTechnologiesByBootcampId(Long bootcampId);
}
