package com.gft.wrk25_communication.communication.infrastructure.controller;

import com.gft.wrk25_communication.communication.application.*;
import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationGetAllUseCase notificationGetAllUseCase;

    @Mock
    private NotificationDeleteByIdUseCase notificationDeleteByIdUseCase;

    @Mock
    private NotificationSetImportantByIdUseCase notificationSetImportantByIdUseCase;

    @InjectMocks
    private NotificationController notificationController;

    private List<NotificationDTO> notificationDTOList;
    private List<Notification> notificationList;

    @BeforeEach
    void setUp() {
        initObjects();
    }

    @Test
    void testGetAllNotificationsFromUserId() {

        UserId userId = new UserId(notificationDTOList.get(0).userId());

        when(notificationGetAllUseCase.execute(userId)).thenReturn(notificationDTOList);

        ResponseEntity<List<NotificationDTO>> response = notificationController.getAllNotificationsFromUserId(userId.userId().toString());

        List<NotificationDTO> actualNotificationDTOs = response.getBody();

        assertNotNull(actualNotificationDTOs);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificationDTOList.size(), actualNotificationDTOs.size());
        assertTrue(notificationDTOList.containsAll(actualNotificationDTOs));
    }

    @Test
    void updateNotificationSetImportant() {

        NotificationId notificationId = notificationList.get(0).getId();

        NotificationDTO notificationDTO = new NotificationDTO(null, null, null, null, true);

        notificationController.updateNotification(notificationId.id(), notificationDTO);

        verify(notificationSetImportantByIdUseCase, times(1)).execute(notificationId, notificationDTO.important());
    }

    @Test
    void updateNotificationSetNotImportant() {

        NotificationId notificationId = notificationList.get(0).getId();

        NotificationDTO notificationDTO = new NotificationDTO(null, null, null, null, false);
        notificationController.updateNotification(notificationId.id(), notificationDTO);

        verify(notificationSetImportantByIdUseCase, times(1)).execute(notificationId, notificationDTO.important());
    }

    @Test
    void deleteNotification() {

        NotificationId notificationId = notificationList.get(0).getId();


        notificationController.deleteNotification(notificationId.id());

        verify(notificationDeleteByIdUseCase, times(1)).execute(notificationId);
    }

    private void initObjects() {

        NotificationFactory factory = new NotificationFactory();

        notificationDTOList = new LinkedList<>();
        notificationList = new LinkedList<>();

        UserId userId = new UserId(UUID.randomUUID());

        for (int i = 0; i < 10; i++) {
            notificationList.add(
                    factory.reinstantiate(
                            new NotificationId(),
                            Instancio.create(LocalDateTime.class),
                            userId,
                            Instancio.create(String.class),
                            Instancio.create(Boolean.class)
                    )
            );
        }

        for (Notification notification : notificationList) {
            notificationDTOList.add(
                    new NotificationDTO(
                            notification.getId().id(),
                            notification.getCreatedAt(),
                            notification.getUserId().userId(),
                            notification.getMessage(),
                            notification.isImportant()
                    )
            );
        }

    }

}