package com.app.reactive_capability_microservice.infrastructure.drivenadapter.technologyservice.adapter;

import com.app.reactive_capability_microservice.domain.model.Technology;
import com.app.reactive_capability_microservice.domain.records.InvalidTechnologyValidationResult;
import com.app.reactive_capability_microservice.domain.spi.ITechnologyGateway;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.technologyservice.dto.CapabilityTechnologyRequestDTO;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.technologyservice.dto.ValidationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class TechnologyWebClientAdapter implements ITechnologyGateway {

    private final WebClient webClient;

    @Override
    public Mono<InvalidTechnologyValidationResult> validateTechnologyIdsExist(Flux<Long> technologyIds) {
        return technologyIds
                .collectList()
                .flatMap(ids -> webClient
                        .post()
                        .uri("/api/technologies/validation")
                        .bodyValue(Collections.singletonMap("technologyIds", ids))
                        .retrieve()
                        .bodyToMono(ValidationResponseDTO.class)
                )
                .map(e -> new InvalidTechnologyValidationResult(e.isValid(), e.getInvalidIds()));
    }

    @Override
    public Mono<Void> createCapabilityTechnologyAssociations(Long capabilityId, Flux<Long> technologyIds) {
        return technologyIds
                .collectList()
                .map(ids -> new CapabilityTechnologyRequestDTO(capabilityId, ids))
                .flatMap(dto ->
                        webClient
                                .post()
                                .uri("/api/capability-tech")
                                .bodyValue(dto)
                                .retrieve()
                                .bodyToMono(Void.class)
                );
    }

    @Override
    public Mono<List<Technology>> getTechnologiesByCapabilityId(Long capabilityId) {
        return webClient
                .get()
                .uri("/api/technologies/by-capability/{id}", capabilityId)
                .retrieve()
                .bodyToFlux(Technology.class)
                .collectList();
    }

}
