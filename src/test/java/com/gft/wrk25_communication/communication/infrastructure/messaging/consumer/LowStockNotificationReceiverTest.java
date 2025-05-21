package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.LowStockNotificationDTO;
import com.gft.wrk25_communication.communication.domain.ProductId;
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

@ExtendWith(MockitoExtension.class)
class LowStockNotificationReceiverTest {

    @Mock
    private NotificationFactory notificationFactory;

    @Mock
    private NotificationSaveUseCase notificationSaveUseCase;

    @InjectMocks
    private LowStockNotificationReceiver receiver;

    @Test
    void testReceive() {

        NotificationFactory workingFactory = new NotificationFactory();

        UserId userId = new UserId(UUID.randomUUID());
        ProductId productId = new ProductId(7L);
        Integer quantity = 5;

        LowStockNotificationDTO notification = new LowStockNotificationDTO(userId.id(), productId.id(), quantity);
        Notification notificationToReturn = workingFactory.createLowStockNotification(userId, productId, quantity);

        when(notificationFactory.createLowStockNotification(userId,productId,quantity))
                .thenReturn(notificationToReturn);

        receiver.receive(notification);

        verify(notificationFactory, times(1)).createLowStockNotification(userId,productId,quantity);
        verify(notificationSaveUseCase, times(1)).execute(notificationToReturn);
    }
}
