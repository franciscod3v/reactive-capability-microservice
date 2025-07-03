package com.app.reactive_capability_microservice.domain.usecase;

import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.domain.model.CapabilityWithTechnologies;
import com.app.reactive_capability_microservice.domain.model.Technology;
import com.app.reactive_capability_microservice.domain.spi.ICapabilityPersistencePort;
import com.app.reactive_capability_microservice.domain.spi.ITechnologyGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class GetCapabilitiesWithTechnologiesByBootcampIdUseCaseTest {

    private ICapabilityPersistencePort capabilityPersistencePort;
    private ITechnologyGateway technologyGateway;
    private GetCapabilitiesWithTechnologiesByBootcampIdUseCase useCase;

    @BeforeEach
    void setUp() {
        capabilityPersistencePort = mock(ICapabilityPersistencePort.class);
        technologyGateway = mock(ITechnologyGateway.class);
        useCase = new GetCapabilitiesWithTechnologiesByBootcampIdUseCase(capabilityPersistencePort, technologyGateway);
    }

    @Test
    void shouldReturnCapabilitiesWithTechnologiesByBootcampId() {
        // Arrange
        Long bootcampId = 100L;

        Capability cap1 = new Capability(1L, "Java", "Java backend", null);
        Capability cap2 = new Capability(2L, "React", "Frontend", null);

        List<Capability> capabilities = List.of(cap1, cap2);
        List<Technology> techs1 = List.of(new Technology(11L, "Spring"), new Technology(12L, "JUnit"));
        List<Technology> techs2 = List.of(new Technology(21L, "ReactJS"));

        when(capabilityPersistencePort.getCapabilitiesByBootcampId(bootcampId)).thenReturn(Mono.just(capabilities));
        when(technologyGateway.getTechnologiesByCapabilityId(1L)).thenReturn(Mono.just(techs1));
        when(technologyGateway.getTechnologiesByCapabilityId(2L)).thenReturn(Mono.just(techs2));

        // Act
        Mono<List<CapabilityWithTechnologies>> result = useCase.getCapabilitiesWithTechnologiesByBootcampId(bootcampId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(list -> list.size() == 2 &&
                        list.get(0).getTechnologies().equals(techs1) &&
                        list.get(1).getTechnologies().equals(techs2))
                .verifyComplete();

        verify(capabilityPersistencePort).getCapabilitiesByBootcampId(bootcampId);
        verify(technologyGateway).getTechnologiesByCapabilityId(1L);
        verify(technologyGateway).getTechnologiesByCapabilityId(2L);
    }
}
