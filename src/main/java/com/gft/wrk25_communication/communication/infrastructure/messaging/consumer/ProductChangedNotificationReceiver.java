package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductStockChangedNotificationDTO;
import com.gft.wrk25_communication.communication.application.web.ApiClient;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.infrastructure.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductChangedNotificationReceiver {

    private final ApiClient apiClient;
    private final NotificationFactory notificationFactory;
    private final NotificationSaveUseCase notificationSaveUseCase;

    @RabbitListener(queues = "${queue.product.stock.changed}")
    public void receive(ProductStockChangedNotificationDTO notification) {

        ProductId productId = new ProductId(notification.id());

        ProductDTO product = apiClient.getProductById(productId);

        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        List<UserId> users = apiClient.getUsersThatHaveProductInFavorites(productId);

        if (!users.isEmpty()) {

            users.forEach(userId -> {
                Notification notificationToSave = notificationFactory.createProductChangedNotification(
                        userId,
                        product
                );

                notificationSaveUseCase.execute(notificationToSave);
            });
        }
    }

}
