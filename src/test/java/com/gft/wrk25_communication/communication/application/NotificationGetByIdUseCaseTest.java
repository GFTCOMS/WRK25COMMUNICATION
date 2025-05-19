package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.UserId;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationGetByIdUseCaseTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    NotificationGetByIdUseCase notificationGetByIdUseCase;

    @Test
    void testExecute() {
        NotificationFactory notificationFactory = new NotificationFactory();

        NotificationId notificationId = new NotificationId();

        Notification notification = notificationFactory.reinstantiate(
                notificationId,
                Instancio.create(LocalDateTime.class),
                new UserId(UUID.randomUUID()),
                Instancio.create(String.class),
                Instancio.create(Boolean.class)
        );

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        Optional<Notification> actualNotification = notificationGetByIdUseCase.execute(notificationId);

        assertTrue(actualNotification.isPresent());
        assertEquals(notification.getId(), actualNotification.get().getId());
        assertEquals(notification.getCreatedAt(), actualNotification.get().getCreatedAt());
        assertEquals(notification.getUserId(), actualNotification.get().getUserId());
        assertEquals(notification.getMessage(), actualNotification.get().getMessage());
        assertEquals(notification.isImportant(), actualNotification.get().isImportant());
    }
}