package com.app.reactive_capability_microservice.infrastructure.entrypoints.mapper;

import com.app.reactive_capability_microservice.domain.model.BootcampCapability;
import com.app.reactive_capability_microservice.infrastructure.entrypoints.dto.BootCampCapabilityRequestDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class BootcampCapabilityMapperDTO {

    public Flux<BootcampCapability> toModel (BootCampCapabilityRequestDTO dto) {
        return Flux.fromIterable(dto.getCapabilitiesIds())
                .map(capaId -> new BootcampCapability(null, capaId, dto.getIdBootcamp()));
    }
}
