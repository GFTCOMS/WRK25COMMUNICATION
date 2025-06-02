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
    void testReceive() {
        UserDeletedDTO userDeletedDTO = Instancio.create(UserDeletedDTO.class);

        when(tracer.nextSpan()).thenReturn(span);
        when(span.name(anyString())).thenReturn(span);
        when(span.start()).thenReturn(span);
        when(tracer.withSpan(span)).thenReturn(spanInScope);

        userDeletedReceiver.receive(userDeletedDTO);

        verify(tracer).nextSpan();
        verify(span).name("process.user-deleted-message");
        verify(span).start();
        verify(tracer).withSpan(span);
        verify(notificationDeleteByUserIdUseCase).execute(new UserId(userDeletedDTO.id()));
        verify(span).end();
        verifyNoMoreInteractions(notificationDeleteByUserIdUseCase, tracer, span);
    }
}
