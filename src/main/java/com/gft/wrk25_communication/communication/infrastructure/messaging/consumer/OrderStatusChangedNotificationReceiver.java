package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.OrderStatusChangedNotificationDTO;
import com.gft.wrk25_communication.communication.domain.OrderId;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderStatusChangedNotificationReceiver {

    private final NotificationSaveUseCase notificationSaveUseCase;
    private final NotificationFactory notificationFactory;

    @RabbitListener(queues = "${queue.order.state.changed}")
    public void receive(OrderStatusChangedNotificationDTO notification) {
        log.info("Recibida OrderStatusChangedNotification: userId={}, orderId={}, status={}",
                notification.userId(), notification.orderId(), notification.orderStatus());

        Notification notificationToSave = notificationFactory.createOrderStatusChangedNotification(
                new UserId(notification.userId()),
                new OrderId(notification.orderId()),
                notification.orderStatus()
        );

        notificationSaveUseCase.execute(notificationToSave);
    }
}
