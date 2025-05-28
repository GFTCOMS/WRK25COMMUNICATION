package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.LowStockNotificationDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.application.web.ApiClient;
import com.gft.wrk25_communication.communication.domain.ProductId;
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
public class LowStockNotificationReceiver {

    private final ApiClient apiClient;
    private final NotificationSaveUseCase notificationSaveUseCase;
    private final NotificationFactory notificationFactory;

    @RabbitListener(queues = "${queue.product.stock.low}")
    public void receive(LowStockNotificationDTO notification) {
        log.info("Recibida notificación de LowStockNotification: userId={}, productId={}, stock={}",
                notification.userId(), notification.productId(), notification.stock());

        ProductDTO productDTO = apiClient.getProductById(new ProductId(notification.productId()));

            Notification notificationToSave = notificationFactory.createLowStockNotification(
                    new UserId(notification.userId()),
                    productDTO
            );

            notificationSaveUseCase.execute(notificationToSave);
    }

}
