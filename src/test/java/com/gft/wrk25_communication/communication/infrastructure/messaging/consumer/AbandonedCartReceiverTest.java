package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.AbandonedCartDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AbandonedCartReceiverTest {

    @Mock
    private NotificationSaveUseCase notificationSaveUseCase;

    @InjectMocks
    private AbandonedCartReceiver abandonedCartReceiver;

    @Mock
    private NotificationFactory notificationFactory;

    @Test
    void testReceive() {
        NotificationFactory workingFactory = new NotificationFactory();

        UUID uuid = UUID.randomUUID();
        UserId userId = new UserId(uuid);
        AbandonedCartDTO dto = new AbandonedCartDTO(userId);

        Notification expected = workingFactory.createAbandonedCartNotification(userId);

        when(notificationFactory.createAbandonedCartNotification(userId))
                .thenReturn(expected);

        abandonedCartReceiver.handleMessage(dto);

        verify(notificationFactory, times(1)).createAbandonedCartNotification(userId);
        verify(notificationSaveUseCase, times(1)).execute(expected);

    }
}