package com.gft.wrk25_communication.communication.domain.notification;

import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Component
public class NotificationFactory {

    public Notification reinstantiate(NotificationId id, LocalDateTime createdAt, UserId userId, String message) {
        return new Notification(id, createdAt, userId, message);
    }

    public Notification createProductStockChanged(UserId userId, ProductId productId, Integer stock) {

        Assert.notNull(stock, "Stock must not be null");

        String message = String.format("The stock of the product \"%s\" has been changed to: %d.", productId.id(), stock);

        return new Notification(userId, message);
    }

}
