package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.CartProductChangedDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
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
public class ProductInCartChangedNotificationReceiver {

    private final ApiClient apiClient;
    private final NotificationFactory notificationFactory;
    private final NotificationSaveUseCase notificationSaveUseCase;

    @RabbitListener(queues = "${queue.cart.product.changed}")
    public void receive(CartProductChangedDTO notification) {

        log.info("CartProductChanged notification received: userId={}, productId={}",
                notification.userId() ,notification.productId());

        UserId userId = new UserId(notification.userId());

        ProductId productId = new ProductId(notification.productId());

        ProductDTO product = apiClient.getProductById(productId);

        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        Notification notificationToSave = notificationFactory.createProductChangedNotification(userId, product);

        notificationSaveUseCase.execute(notificationToSave);
    }

}
