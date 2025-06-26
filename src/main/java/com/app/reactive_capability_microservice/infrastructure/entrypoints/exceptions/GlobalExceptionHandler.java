package com.app.reactive_capability_microservice.infrastructure.entrypoints.exceptions;

import com.app.reactive_capability_microservice.domain.enums.TechnicalMessage;
import com.app.reactive_capability_microservice.domain.exception.BussinesException;
import com.app.reactive_capability_microservice.domain.exception.TechnicalException;
import com.app.reactive_capability_microservice.infrastructure.entrypoints.dto.ErrorDTO;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties.Resources resources,
            ApplicationContext applicationContext,
            ServerCodecConfigurer codecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        super.setMessageReaders(codecConfigurer.getReaders());
        super.setMessageWriters(codecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = getError(request);
        ErrorDTO body;
        HttpStatus status;

        switch (error) {

            case BussinesException be -> {

                var tm = be.getTechnicalMessage();

                try {
                    status = HttpStatus.valueOf(Integer.parseInt(tm.getCode()));
                } catch (Exception e) {
                    status = HttpStatus.BAD_REQUEST;
                }

                body = ErrorDTO
                        .builder()
                        .code(tm.getCode())
                        .message(tm.getMessage())
                        .param(tm.getParam().isEmpty() ? null : tm.getParam())
                        .build();

            }
            case TechnicalException te -> {
                var tm = te.getTechnicalMessage();

                try {
                    status = HttpStatus.valueOf(Integer.parseInt(tm.getCode()));
                } catch (Exception e) {
                    status = HttpStatus.BAD_REQUEST;
                }

                body = ErrorDTO.builder()
                        .code(tm.getCode())
                        .message(tm.getMessage())
                        .param(tm.getParam().isEmpty() ? null : tm.getParam())
                        .build();
            }
            default -> {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                body = ErrorDTO.builder()
                        .code(TechnicalMessage.INTERNAL_ERROR.getCode())
                        .message(TechnicalMessage.INTERNAL_ERROR.getMessage())
                        .param(null)
                        .build();
            }
        }

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body);

    }
}
