package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.LowStockNotificationDTO;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LowStockNotificationReceiver {

    private final NotificationSaveUseCase notificationSaveUseCase;

    private final NotificationFactory notificationFactory;

    @RabbitListener(queues = "${queue.product.stock.low}")
    public void receive(LowStockNotificationDTO notification) {

            Notification notificationToSave = notificationFactory.createLowStockNotification(
                    new UserId(notification.userId()),
                    new ProductId(notification.productId()),
                    notification.quantity()
            );

            notificationSaveUseCase.execute(notificationToSave);
    }

}
