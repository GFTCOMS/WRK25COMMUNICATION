package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationDeleteByIdUseCaseTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationDeleteByIdUseCase notificationDeleteByIdUseCase;

    @Test
    void testExecute() {

        NotificationId notificationId = new NotificationId();

        notificationDeleteByIdUseCase.execute(notificationId);

        verify(notificationRepository, times(1)).deleteById(notificationId);
    }
}