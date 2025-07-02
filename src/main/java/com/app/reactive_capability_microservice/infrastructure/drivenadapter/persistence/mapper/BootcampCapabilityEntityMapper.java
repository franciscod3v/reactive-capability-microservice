package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper;

import com.app.reactive_capability_microservice.domain.model.BootcampCapability;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.entity.BootcampCapabilityEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class BootcampCapabilityEntityMapper {
    public static final ObjectMapper mapper = new ObjectMapper();

    public BootcampCapabilityEntity toEntity(BootcampCapability bootcampCapability) {
        return mapper.convertValue(bootcampCapability, BootcampCapabilityEntity.class);
    }

    public BootcampCapability toModel(BootcampCapabilityEntity bootcampCapabilityEntity) {
        return mapper.convertValue(bootcampCapabilityEntity, BootcampCapability.class);
    }
}
