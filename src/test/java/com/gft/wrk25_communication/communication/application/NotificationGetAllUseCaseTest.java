package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationGetAllUseCaseTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationGetAllUseCase notificationGetAllUseCase;

    @Test
    void testExecute() {

        NotificationFactory notificationFactory = new NotificationFactory();

        List<Notification> notifications = new ArrayList<>();

        UserId userId = new UserId(UUID.randomUUID());

        for (int i = 0; i < 10; i++) {
            notifications.add(notificationFactory.reinstantiate(
                    new NotificationId(),
                    Instancio.create(LocalDateTime.class),
                    userId,
                    Instancio.create(String.class),
                    Instancio.create(Boolean.class)
            ));
        }

        List<NotificationDTO> notificationDTOs = getNotificationDTOs(notifications);

        when(notificationRepository.findAllByUserId(userId)).thenReturn(notifications);

        List<NotificationDTO> actualNotifications = notificationGetAllUseCase.execute(userId);

        assertEquals(notifications.size(), actualNotifications.size());
        assertTrue(actualNotifications.containsAll(notificationDTOs));
    }

    private static List<NotificationDTO> getNotificationDTOs(List<Notification> notifications) {
        List<NotificationDTO> notificationDTOs = new ArrayList<>();

        for (Notification notification: notifications) {
            notificationDTOs.add(
                    new NotificationDTO(
                            notification.getId().id(),
                            notification.getCreatedAt(),
                            notification.getUserId().id(),
                            notification.getMessage(),
                            notification.isImportant()
                    )
            );
        }
        return notificationDTOs;
    }
}