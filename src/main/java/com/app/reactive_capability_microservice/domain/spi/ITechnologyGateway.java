package com.app.reactive_capability_microservice.domain.spi;

import com.app.reactive_capability_microservice.domain.model.Technology;
import com.app.reactive_capability_microservice.domain.records.InvalidTechnologyValidationResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyGateway {

    Mono<InvalidTechnologyValidationResult> validateTechnologyIdsExist(Flux<Long> technologyIds);

    Mono<Void> createCapabilityTechnologyAssociations(Long capabilityId, Flux<Long> technologyIds);

    Mono<List<Technology>> getTechnologiesByCapabilityId(Long capabilityId);

}
