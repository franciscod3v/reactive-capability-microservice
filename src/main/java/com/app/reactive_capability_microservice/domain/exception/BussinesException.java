package com.app.reactive_capability_microservice.domain.exception;

import com.app.reactive_capability_microservice.domain.enums.TechnicalMessage;


public class BussinesException extends CustomException {


    public BussinesException(TechnicalMessage technicalMessage) {

        super(technicalMessage.getMessage(), technicalMessage);
    }

}