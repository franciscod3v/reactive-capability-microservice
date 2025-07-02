package com.app.reactive_capability_microservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootcampCapability {
    private Long id;
    private Long idCapability;
    private Long idBootcamp;
}
