package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationGetByIdUseCaseTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    NotificationExistsByIdUseCase notificationExistsByIdUseCase;

    @Test
    void testExecute() {

        NotificationId notificationId = new NotificationId();

        when(notificationRepository.existsById(notificationId)).thenReturn(true);

        boolean result = notificationExistsByIdUseCase.execute(notificationId);

        assertTrue(result);
    }
}