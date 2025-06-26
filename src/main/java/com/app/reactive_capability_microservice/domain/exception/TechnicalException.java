package com.app.reactive_capability_microservice.domain.exception;

import com.app.reactive_capability_microservice.domain.enums.TechnicalMessage;

public class TechnicalException extends CustomException {

  public TechnicalException(Throwable cause, TechnicalMessage message) {
    super(cause, message);
  }

  public TechnicalException(TechnicalMessage message) {
    super(message.getMessage(), message);
  }
}
