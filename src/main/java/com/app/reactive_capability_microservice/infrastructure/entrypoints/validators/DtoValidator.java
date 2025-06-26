package com.app.reactive_capability_microservice.infrastructure.entrypoints.validators;

import com.app.reactive_capability_microservice.domain.enums.TechnicalMessage;
import com.app.reactive_capability_microservice.domain.exception.TechnicalException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DtoValidator {

    private final Validator validator;

    public <T> Mono<T> validateDto(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (violations.isEmpty()) return Mono.just(dto);

        ConstraintViolation<T> violation = violations.iterator().next(); // primer error
        TechnicalMessage technicalMessage = mapViolationToTechnicalMessage(violation);
        return Mono.error(new TechnicalException(technicalMessage));
    }

    private TechnicalMessage mapViolationToTechnicalMessage(ConstraintViolation<?> violation) {
        String field = violation.getPropertyPath().toString();
        Object annotation = violation.getConstraintDescriptor().getAnnotation();

        if (field.equals("name")) {
            if (annotation instanceof NotBlank) return TechnicalMessage.NAME_REQUIRED;
            if (annotation instanceof Size) return TechnicalMessage.MAXIMUM_CHARACTERS_FOR_NAME;
        }

        if (field.equals("description")) {
            if (annotation instanceof NotBlank) return TechnicalMessage.DESCRIPTION_REQUIRED;
            if (annotation instanceof Size) return TechnicalMessage.MAXIMUM_CHARACTERS_FOR_DESCRIPTION;
        }

        return TechnicalMessage.INTERNAL_ERROR;
    }
}
