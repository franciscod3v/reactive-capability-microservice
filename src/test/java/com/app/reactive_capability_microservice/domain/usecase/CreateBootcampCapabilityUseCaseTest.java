package com.app.reactive_capability_microservice.domain.usecase;

import com.app.reactive_capability_microservice.domain.model.BootcampCapability;
import com.app.reactive_capability_microservice.domain.spi.IBootcampCapabilityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CreateBootcampCapabilityUseCaseTest {

    private IBootcampCapabilityPersistencePort persistencePort;
    private CreateBootcampCapabilityUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IBootcampCapabilityPersistencePort.class);
        useCase = new CreateBootcampCapabilityUseCase(persistencePort);
    }

    @Test
    void shouldSaveAllBootcampCapabilitiesSuccessfully() {
        // Arrange
        BootcampCapability bc1 = new BootcampCapability(null, 1L, 100L);
        BootcampCapability bc2 = new BootcampCapability(null, 2L, 100L);

        Flux<BootcampCapability> inputFlux = Flux.just(bc1, bc2);
        when(persistencePort.saveAllBootcampCapability(inputFlux)).thenReturn(inputFlux);

        // Act
        Flux<BootcampCapability> result = useCase.saveAllBootcampCapability(inputFlux);

        // Assert
        StepVerifier.create(result)
                .expectNext(bc1)
                .expectNext(bc2)
                .verifyComplete();

        verify(persistencePort).saveAllBootcampCapability(inputFlux);
    }
}
