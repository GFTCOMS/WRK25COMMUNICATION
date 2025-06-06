package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.LowStockNotificationDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductStockChangedNotificationDTO;
import com.gft.wrk25_communication.communication.application.web.ApiClient;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.infrastructure.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LowStockNotificationReceiver {

    private final ApiClient apiClient;
    private final NotificationSaveUseCase notificationSaveUseCase;
    private final NotificationFactory notificationFactory;

    @RabbitListener(queues = "${queue.product.stock.changed}")
    public void receive(LowStockNotificationDTO notification) {

        log.info("LowStockNotification notification received: productId={}, stock={}",
                notification.productId(), notification.stock());
        ProductId productId = new ProductId(notification.productId());

        ProductDTO product = apiClient.getProductById(productId);

        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        List<UserId> users = apiClient.getUsersThatHaveProductInFavorites(productId);

        if (!users.isEmpty()) {

            users.forEach(userId -> {
                Notification notificationToSave = notificationFactory.createLowStockNotification(
                        userId,
                        product
                );

                notificationSaveUseCase.execute(notificationToSave);
            });
        }
    }

}
