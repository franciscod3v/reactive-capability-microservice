package com.app.reactive_capability_microservice.infrastructure.entrypoints.router;

import com.app.reactive_capability_microservice.infrastructure.entrypoints.handler.IBootcampCapabilityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BootcampCapabilityRouterRest {

    @Bean(name = "bootcampCapabilityRouterRestBean")
    public RouterFunction<ServerResponse> capabilityTechnologyRouter (IBootcampCapabilityHandler handler) {
        return route(POST("/api/bootcamp-capa"), handler::listenPOSTCreateBootcampCapability);
    }
}
