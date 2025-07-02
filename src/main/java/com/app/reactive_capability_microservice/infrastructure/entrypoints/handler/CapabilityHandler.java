package com.app.reactive_capability_microservice.infrastructure.entrypoints.handler;

import com.app.reactive_capability_microservice.domain.api.ICapabilityServicePort;
import com.app.reactive_capability_microservice.domain.api.IGetAllCapacitiesServicePort;
import com.app.reactive_capability_microservice.domain.enums.TechnicalMessage;
import com.app.reactive_capability_microservice.domain.exception.BusinessException;
import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.domain.spi.ITechnologyGateway;
import com.app.reactive_capability_microservice.infrastructure.entrypoints.dto.CapabilityRequestDTO;
import com.app.reactive_capability_microservice.infrastructure.entrypoints.mapper.CapabilityMapperDTO;
import com.app.reactive_capability_microservice.infrastructure.entrypoints.validators.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class CapabilityHandler implements ICapabilityHandler {

    private final ICapabilityServicePort capabilityServicePort;
    private final ITechnologyGateway technologyGateway;
    private final CapabilityMapperDTO mapper;
    private final DtoValidator dtoValidator;
    private final IGetAllCapacitiesServicePort getAllCapacitiesServicePort;

    @Override
    public Mono<ServerResponse> listenPOSTCreateCapability(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CapabilityRequestDTO.class)
                .flatMap(dtoValidator::validateDto)
                .flatMap(dto -> {
                    if (dto.getTechnologyIds() == null || dto.getTechnologyIds().size() < 3) {
                        return Mono.error(new BusinessException(TechnicalMessage.MINIMUM_TECHNOLOGIES_REQUIRED));
                    }

                    if (dto.getTechnologyIds().size() > 20) {
                        return Mono.error(new BusinessException(TechnicalMessage.MAXIMUM_TECHNOLOGIES_ALLOWED));
                    }

                    long uniqueCount = dto.getTechnologyIds().stream().distinct().count();
                    if (uniqueCount < dto.getTechnologyIds().size()) {
                        return Mono.error(new BusinessException(TechnicalMessage.DUPLICATE_TECHNOLOGIES));
                    }

                    return technologyGateway.validateTechnologyIdsExist(Flux.fromIterable(dto.getTechnologyIds()))
                            .flatMap(validation -> {
                                if (!validation.valid()) {
                                    return Mono.error(new BusinessException(TechnicalMessage.BAD_REQUEST));
                                }

                                Capability model = mapper.toModel(dto);

                                return capabilityServicePort.createCapability(model)
                                        .flatMap(saved ->
                                                technologyGateway.createCapabilityTechnologyAssociations(
                                                        saved.getId(), Flux.fromIterable(dto.getTechnologyIds())
                                                ).then(ServerResponse.created(URI.create("/api/capabilities/" + saved.getId())).build())
                                        );
                            });
                });
    }

    @Override
    public Mono<ServerResponse> listenGETCapabilities(ServerRequest request) {
        String sortBy = request.queryParam("sortBy").orElse("name");
        Boolean asc = Boolean.parseBoolean(request.queryParam("asc").orElse("true"));
        Integer page = Integer.parseInt(request.queryParam("page").orElse("0"));
        Integer size = Integer.parseInt(request.queryParam("size").orElse("10"));

        return getAllCapacitiesServicePort
                .getAllCapabilities(sortBy, asc, page, size)
                .collectList()
                .flatMap(result -> ServerResponse.ok().bodyValue(result));
    }
}
