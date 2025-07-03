package com.app.reactive_capability_microservice.domain.usecase;

import com.app.reactive_capability_microservice.domain.enums.TechnicalMessage;
import com.app.reactive_capability_microservice.domain.exception.BusinessException;
import com.app.reactive_capability_microservice.domain.model.Capability;
import com.app.reactive_capability_microservice.domain.spi.ICapabilityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CreateCapabilityUseCaseTest {

    private ICapabilityPersistencePort persistencePort;
    private CreateCapabilityUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(ICapabilityPersistencePort.class);
        useCase = new CreateCapabilityUseCase(persistencePort);
    }

    @Test
    void shouldCreateCapabilityWhenNotExists() {
        // Given
        Capability capability = new Capability(1L, "Java", "Backend", null);

        when(persistencePort.findByName("Java")).thenReturn(Mono.empty());
        when(persistencePort.createCapability(capability)).thenReturn(Mono.just(capability));

        // When
        Mono<Capability> result = useCase.createCapability(capability);

        // Then
        StepVerifier.create(result)
                .expectNext(capability)
                .verifyComplete();

        verify(persistencePort).findByName("Java");
        verify(persistencePort).createCapability(capability);
    }

    @Test
    void shouldThrowExceptionWhenCapabilityAlreadyExists() {
        // Given
        Capability capability = new Capability(1L, "Java", "Backend", null);

        when(persistencePort.findByName("Java")).thenReturn(Mono.just(capability));

        // When
        Mono<Capability> result = useCase.createCapability(capability);

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof BusinessException &&
                                ((BusinessException) error).getTechnicalMessage() == TechnicalMessage.CAPABILITY_ALREADY_EXISTS
                )
                .verify();

        verify(persistencePort).findByName("Java");
        verify(persistencePort, never()).createCapability(any());
    }
}
