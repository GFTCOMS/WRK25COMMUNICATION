package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationSetNotImportantByIdUseCaseTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationSetNotImportantByIdUseCase notificationSetNotImportantByIdUseCase;

    @Test
    void testExecute() {

        NotificationId notificationId = new NotificationId();

        notificationSetNotImportantByIdUseCase.execute(notificationId);

        verify(notificationRepository, Mockito.times(1)).setAsNotImportant(notificationId);
    }
}