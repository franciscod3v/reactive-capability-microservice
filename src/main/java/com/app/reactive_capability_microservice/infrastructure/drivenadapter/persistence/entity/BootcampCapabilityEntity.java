package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("capabilities_bootcamps")
public class BootcampCapabilityEntity {

    @Id
    private Long id;
    private Long idCapability;
    private Long idBootcamp;
}
