package com.app.reactive_capability_microservice.domain.usecase;

import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.domain.model.CapabilityWithTechnologies;
import com.app.reactive_capability_microservice.domain.model.Technology;
import com.app.reactive_capability_microservice.domain.spi.IGetAllCapabilitiesPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.ITechnologyGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class GetAllCapabilitiesUseCaseTest {

    private IGetAllCapabilitiesPersistencePort capabilityPersistence;
    private ITechnologyGateway technologyGateway;
    private GetAllCapabilitiesUseCase useCase;

    @BeforeEach
    void setUp() {
        capabilityPersistence = mock(IGetAllCapabilitiesPersistencePort.class);
        technologyGateway = mock(ITechnologyGateway.class);
        useCase = new GetAllCapabilitiesUseCase(capabilityPersistence, technologyGateway);
    }

    @Test
    void shouldReturnAllCapabilitiesWithTechnologies() {
        // Arrange
        Capability cap1 = new Capability(1L, "Java", "Backend Java", List.of(10L, 11L));
        Capability cap2 = new Capability(2L, "React", "Frontend", List.of(20L));

        List<Technology> techs1 = List.of(new Technology(10L, "Spring Boot"), new Technology(11L, "JUnit"));
        List<Technology> techs2 = List.of(new Technology(20L, "ReactJS"));

        when(capabilityPersistence.getAllCapabilities("name", true, 0, 10))
                .thenReturn(Flux.just(cap1, cap2));

        when(technologyGateway.getTechnologiesByCapabilityId(1L)).thenReturn(Mono.just(techs1));
        when(technologyGateway.getTechnologiesByCapabilityId(2L)).thenReturn(Mono.just(techs2));

        // Act
        Flux<CapabilityWithTechnologies> result = useCase.getAllCapabilities("name", true, 0, 10);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(cwt -> cwt.getId().equals(1L) && cwt.getTechnologies().equals(techs1))
                .expectNextMatches(cwt -> cwt.getId().equals(2L) && cwt.getTechnologies().equals(techs2))
                .verifyComplete();

        verify(capabilityPersistence).getAllCapabilities("name", true, 0, 10);
        verify(technologyGateway).getTechnologiesByCapabilityId(1L);
        verify(technologyGateway).getTechnologiesByCapabilityId(2L);
    }
}
