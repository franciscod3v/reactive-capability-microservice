package com.app.reactive_capability_microservice.infrastructure.entrypoints.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ICapabilityHandler {
    Mono<ServerResponse> listenPOSTCreateCapability(ServerRequest serverRequest);

    Mono<ServerResponse> listenGETCapabilities(ServerRequest request);
}
