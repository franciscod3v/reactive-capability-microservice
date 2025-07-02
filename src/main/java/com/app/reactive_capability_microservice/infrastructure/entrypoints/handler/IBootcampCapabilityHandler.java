package com.app.reactive_capability_microservice.infrastructure.entrypoints.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IBootcampCapabilityHandler {
    Mono<ServerResponse> listenPOSTCreateBootcampCapability(ServerRequest serverRequest);
}
