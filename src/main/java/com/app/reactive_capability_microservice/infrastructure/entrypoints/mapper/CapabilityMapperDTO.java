package com.app.reactive_capability_microservice.infrastructure.entrypoints.mapper;

import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.infrastructure.entrypoints.dto.CapabilityRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CapabilityMapperDTO {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Capability toModel(CapabilityRequestDTO dto) {
        return MAPPER.convertValue(dto, Capability.class);
    }
}
