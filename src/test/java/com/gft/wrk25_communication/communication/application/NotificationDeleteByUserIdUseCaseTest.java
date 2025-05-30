package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.event.EventPublisher;
import com.gft.wrk25_communication.communication.domain.event.UserDeletedEvent;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationDeleteByUserIdUseCaseTest {

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    EventPublisher eventPublisher;

    @InjectMocks
    NotificationDeleteByUserIdUseCase notificationDeleteByUserIdUseCase;

    @Test
    void testExecute() {

        UserId userId = Instancio.create(UserId.class);

        notificationDeleteByUserIdUseCase.execute(userId);

        verify(notificationRepository, times(1)).deleteAllByUserId(userId);
        verify(eventPublisher, times(1)).publishUserDeletedEvent(new UserDeletedEvent(userId));
    }
}