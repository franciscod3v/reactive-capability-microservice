package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository;

import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.entity.CapabilityEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapabilityRepository extends R2dbcRepository<CapabilityEntity, Long> {

    Mono<CapabilityEntity> findByName(String name);

    @Query("""
    SELECT c.id, c.name, c.description
    FROM capabilities c
    INNER JOIN capabilities_bootcamps cb ON cb.id_capability = c.id
    WHERE cb.id_bootcamp = :bootcampId
    """)
    Flux<CapabilityEntity> findAllByBootcampId(Long bootcampId);
}
