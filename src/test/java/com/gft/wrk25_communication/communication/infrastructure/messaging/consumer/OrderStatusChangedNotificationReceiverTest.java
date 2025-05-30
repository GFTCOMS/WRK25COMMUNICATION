package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.OrderStatusChangedNotificationDTO;
import com.gft.wrk25_communication.communication.domain.OrderId;
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
class OrderStatusChangedNotificationReceiverTest {

    @Mock
    private NotificationFactory notificationFactory;

    @Mock
    private NotificationSaveUseCase notificationSaveUseCase;

    @InjectMocks
    private OrderStatusChangedNotificationReceiver receiver;

    @Test
    void testReceive() {

        NotificationFactory workingFactory = new NotificationFactory();

        UserId userId = new UserId(UUID.randomUUID());
        OrderId orderId = new OrderId(UUID.randomUUID());
        String orderStatus = "IN_DELIVERY";

        OrderStatusChangedNotificationDTO notification = new OrderStatusChangedNotificationDTO(userId.userId(), orderId.id(), orderStatus);
        Notification notificationToReturn = workingFactory.createOrderStatusChangedNotification(userId, orderId, orderStatus);

        when(notificationFactory.createOrderStatusChangedNotification(userId, orderId, orderStatus))
                .thenReturn(notificationToReturn);

        receiver.receive(notification);

        verify(notificationFactory, times(1)).createOrderStatusChangedNotification(userId,orderId, orderStatus);
        verify(notificationSaveUseCase, times(1)).execute(notificationToReturn);
    }
}
