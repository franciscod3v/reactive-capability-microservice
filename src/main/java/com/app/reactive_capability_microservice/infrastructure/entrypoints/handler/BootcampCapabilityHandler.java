package com.app.reactive_capability_microservice.infrastructure.entrypoints.handler;

import com.app.reactive_capability_microservice.domain.api.IBootcampCapabilityServicePort;
import com.app.reactive_capability_microservice.domain.usecase.CreateBootcampCapabilityUseCase;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper.BootcampCapabilityEntityMapper;
import com.app.reactive_capability_microservice.infrastructure.entrypoints.dto.BootCampCapabilityRequestDTO;
import com.app.reactive_capability_microservice.infrastructure.entrypoints.mapper.BootcampCapabilityMapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class BootcampCapabilityHandler implements IBootcampCapabilityHandler{

    private final IBootcampCapabilityServicePort createBootcampCapabilityUseCase;
    private final BootcampCapabilityMapperDTO mapperDTO;

    @Override
    public Mono<ServerResponse> listenPOSTCreateBootcampCapability(ServerRequest serverRequest) {
        return serverRequest.bodyToFlux(BootCampCapabilityRequestDTO.class)
                .flatMap(mapperDTO::toModel)
                .transform(createBootcampCapabilityUseCase::saveAllBootcampCapability)
                .then(ServerResponse.created(URI.create("api/bootcamp-capa")).build());
    }
}
