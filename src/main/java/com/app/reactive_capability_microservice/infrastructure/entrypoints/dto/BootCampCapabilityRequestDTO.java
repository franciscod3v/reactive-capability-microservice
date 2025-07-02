package com.app.reactive_capability_microservice.infrastructure.entrypoints.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootCampCapabilityRequestDTO {
    private Long idBootcamp;
    private List<Long> capabilitiesIds;
}
