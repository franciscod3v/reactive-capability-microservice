package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.mapper;

import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.entity.CapabilityEntity;
import org.springframework.stereotype.Component;

@Component
public class CapabilityEntityMapper {

    public CapabilityEntity toEntity(Capability capability) {
        CapabilityEntity entity = new CapabilityEntity();
        entity.setId(capability.getId()); // opcional si es autogenerado
        entity.setName(capability.getName());
        entity.setDescription(capability.getDescription());
        return entity;
    }

    public Capability toDomain(CapabilityEntity entity) {
        return new Capability(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                null
        );
    }
}
