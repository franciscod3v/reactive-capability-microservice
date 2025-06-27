package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "capabilities")
public class CapabilityEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
