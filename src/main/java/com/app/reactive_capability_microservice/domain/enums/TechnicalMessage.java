package com.app.reactive_capability_microservice.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalMessage {

    // Business Errors
    CAPABILITY_ALREADY_EXISTS("409", "There is already a capability with that name", "name"),
    DUPLICATE_TECHNOLOGIES("400", "Technologies must be unique", "technologiesIds"),
    MINIMUM_TECHNOLOGIES_REQUIRED("400", "A capability must have at least 3 technologies", "technologiesIds"),
    MAXIMUM_TECHNOLOGIES_ALLOWED("400", "A capability can have a maximum of 20 technologies", "technologiesIds"),

    // Validation Errors
    NAME_REQUIRED("400", "The name is required", "name"),
    MAXIMUM_CHARACTERS_FOR_NAME("400", "The name can only have 50 characters", "name"),
    DESCRIPTION_REQUIRED("400", "The description is required", "description"),
    MAXIMUM_CHARACTERS_FOR_DESCRIPTION("400", "The description can only have 90 characters", "description"),

    // Generic Errors
    INTERNAL_ERROR("500", "An unexpected error occurred", ""),
    BAD_REQUEST("400", "The request is malformed or invalid", "");

    private final String code;
    private final String message;
    private final String param;
}
