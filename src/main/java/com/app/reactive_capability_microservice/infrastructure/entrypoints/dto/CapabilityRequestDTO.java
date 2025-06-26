package com.app.reactive_capability_microservice.infrastructure.entrypoints.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapabilityRequestDTO {

    @NotBlank(message = "NAME_REQUIRED")
    @Size(max = 50, message = "MAXIMUM_CHARACTERS_FOR_NAME")
    private String name;

    @NotBlank(message = "DESCRIPTION_REQUIRED")
    @Size(max = 90, message = "MAXIMUM_CHARACTERS_FOR_DESCRIPTION")
    private String description;

    private List<Long> technologyIds;
}
