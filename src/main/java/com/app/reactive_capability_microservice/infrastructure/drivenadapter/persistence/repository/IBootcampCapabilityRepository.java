package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository;

import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.entity.BootcampCapabilityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface IBootcampCapabilityRepository extends R2dbcRepository<BootcampCapabilityEntity, Long> {
}
