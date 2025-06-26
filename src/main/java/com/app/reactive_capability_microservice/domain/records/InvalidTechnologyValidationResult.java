package com.app.reactive_capability_microservice.domain.records;

import java.util.List;

public record InvalidTechnologyValidationResult(boolean valid, List<Long> invalidIds) {
}
