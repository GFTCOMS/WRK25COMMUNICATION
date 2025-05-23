package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import com.gft.wrk25_communication.communication.infrastructure.exception.NotificationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationSetImportantByIdUseCaseTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationSetImportantByIdUseCase notificationSetImportantByIdUseCase;

    @Test
    void testSetImportantTrueExecute() {

        NotificationId notificationId = new NotificationId();

        when(notificationRepository.existsById(notificationId)).thenReturn(true);

        notificationSetImportantByIdUseCase.execute(notificationId, true);

        verify(notificationRepository, times(1)).setImportant(notificationId, true);
    }

    @Test
    void testSetImportantFalseExecute() {

        NotificationId notificationId = new NotificationId();

        when(notificationRepository.existsById(notificationId)).thenReturn(true);

        notificationSetImportantByIdUseCase.execute(notificationId, false);

        verify(notificationRepository, times(1)).setImportant(notificationId, false);
    }

    @Test
    void testSetImportantNullExecute() {

        NotificationId notificationId = new NotificationId();

        assertThrows(IllegalArgumentException.class, () ->
                notificationSetImportantByIdUseCase.execute(notificationId, null));

    }

    @Test
    void testSetImportantIdNotFoundExecute() {

        NotificationId notificationId = new NotificationId();

        when(notificationRepository.existsById(notificationId)).thenReturn(false);

        assertThrows(NotificationNotFoundException.class, () ->
                notificationSetImportantByIdUseCase.execute(notificationId, true));

    }

}