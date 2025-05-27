package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.event.NotificationEventPublisher;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationSaveUseCaseTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private NotificationEventPublisher eventPublisher;

    @InjectMocks
    private NotificationSaveUseCase useCaseToTest;

    private final NotificationFactory workingFactory = new NotificationFactory();

    @Test
    void testExecute() {
        Notification notification = workingFactory.reinstantiate(
                new NotificationId(),
                Instancio.create(LocalDateTime.class),
                Instancio.create(UserId.class),
                Instancio.create(String.class),
                Instancio.create(Boolean.class)
        );

        // 👇 Simular que el repositorio retorna la misma notificación
        when(repository.save(notification)).thenReturn(notification);

        useCaseToTest.execute(notification);

        verify(repository, times(1)).save(notification);
        verify(eventPublisher, times(1))
                .publishNotificationCreatedEvent(any(NotificationCreatedEvent.class));
    }
}
