package com.gft.wrk25_communication.communication.domain.notification;

import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.domain.OrderId;
import com.gft.wrk25_communication.communication.domain.UserId;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Component
public class NotificationFactory {

    public Notification reinstantiate(NotificationId id, LocalDateTime createdAt, UserId userId, String message, boolean important) {
        return new Notification(id, createdAt, userId, message, important);
    }

    public Notification createLowStockNotification(UserId userId, ProductDTO product) {

        Assert.notNull(product, "Product must not be null");

        String message = String.format("The stock of the product \"%s\" is lower than: %d.", product.name(), product.inventoryData().stock());

        return new Notification(userId, message);
    }

    public Notification createOrderStatusChangedNotification(UserId userId, OrderId orderId, String orderStatus) {

        Assert.notNull(orderStatus, "Order status must not be null");

        String message = String.format("The state of the order \"%s\" has changed to %s.", orderId.id(), orderStatus.replace('_', ' '));
        return new Notification(userId, message);
    }

    public Notification createProductChangedNotification(UserId userId, ProductDTO product) {

        Assert.notNull(product, "Product must not be null");
        String message = String.format("The product \"%s\" has changed the stock to %s.", product.name(), product.inventoryData().stock());
        return new Notification(userId, message);
    }

    public Notification createProductRestockNotification(UserId userId, ProductDTO product) {

        Assert.notNull(product, "Product must not be null");
        String message = String.format("The product \"%s\" has been re-stocked with %s units.", product.name(), product.inventoryData().stock());
        return new Notification(userId, message);
    }

        public Notification createAbandonedCartNotification(UserId userId) {
           String message = "Tu carrito ha sido abandonado";
           return new Notification(userId, message);
    }


}
