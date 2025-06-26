package com.app.reactive_capability_microservice.domain.exception;

import com.app.reactive_capability_microservice.domain.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final TechnicalMessage technicalMessage;

    public CustomException(String message, TechnicalMessage technicalMessage) {
        super(message);
        this.technicalMessage = technicalMessage;

    }

    public CustomException(Throwable cause, TechnicalMessage technicalMessage) {
        super(cause);
        this.technicalMessage = technicalMessage;
    }

}
