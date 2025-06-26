package com.app.reactive_capability_microservice.infrastructure.entrypoints.router;

import com.app.reactive_capability_microservice.infrastructure.entrypoints.handler.ICapabilityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CapabilityRouterRest {

    @Bean(name = "capabilityRouterRestBean")
    public RouterFunction<ServerResponse> capabilityTechnologyRouter (ICapabilityHandler handler) {
        return route(POST("/api/capabilities"), handler::listenPOSTCreateCapability);
    }
}
