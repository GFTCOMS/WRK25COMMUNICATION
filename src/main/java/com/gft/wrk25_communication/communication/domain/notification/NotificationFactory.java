package com.gft.wrk25_communication.communication.domain.notification;

import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Component
public class NotificationFactory {

    public Notification reinstantiate(NotificationId id, LocalDateTime createdAt, UserId userId, String message, boolean important) {
        return new Notification(id, createdAt, userId, message, important);
    }

    public Notification createLowStockNotification(UserId userId, ProductId productId, Integer quantity) {

        Assert.notNull(quantity, "Quantity must not be null");

        String message = String.format("The stock of the product \"%s\" is lower than: %d.", productId.id(), quantity);

        return new Notification(userId, message);
    }

}
