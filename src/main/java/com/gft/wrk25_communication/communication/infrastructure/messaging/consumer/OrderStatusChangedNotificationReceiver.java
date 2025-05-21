package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.OrderStatusChangedNotificationDTO;
import com.gft.wrk25_communication.communication.domain.OrderId;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusChangedNotificationReceiver {

    private final NotificationSaveUseCase notificationSaveUseCase;
    private final NotificationFactory notificationFactory;

    @RabbitListener(queues = "${queue.order.state.changed}")
    public void receive(OrderStatusChangedNotificationDTO notification) {

        Notification notificationToSave = notificationFactory.createOrderStatusChangedNotification(
                new UserId(notification.userId()),
                new OrderId(notification.orderId()),
                notification.orderStatus()
        );

        notificationSaveUseCase.execute(notificationToSave);
    }

}
