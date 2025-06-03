package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationDeleteByUserIdUseCase;
import com.gft.wrk25_communication.communication.application.dto.UserDeletedDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDeletedReceiverTest {

    @Mock
    NotificationDeleteByUserIdUseCase notificationDeleteByUserIdUseCase;

    @Mock
    Tracer tracer;

    @Mock
    Span span;

    @Mock
    Tracer.SpanInScope spanInScope;

    @InjectMocks
    UserDeletedReceiver userDeletedReceiver;

    @Test
    void testReceiveSuccess() {
        UserDeletedDTO userDeletedDTO = Instancio.create(UserDeletedDTO.class);

        userDeletedReceiver.receive(userDeletedDTO);

        verify(notificationDeleteByUserIdUseCase, times(1)).execute(new UserId(userDeletedDTO.id()));
    }

}

