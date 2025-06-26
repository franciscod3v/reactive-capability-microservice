package com.app.reactive_capability_microservice.infrastructure.drivenadapter.technologyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapabilityTechnologyRequestDTO {
    private Long idCapability;
    private List<Long> technologiesIds;
}
