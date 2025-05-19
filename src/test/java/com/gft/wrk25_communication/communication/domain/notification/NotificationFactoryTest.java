package com.gft.wrk25_communication.communication.domain.notification;

import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class NotificationFactoryTest {

    @Test
    void testReinstantiate() {

        UUID idUuid = UUID.randomUUID();
        UUID userIdUuid = UUID.randomUUID();

        NotificationId id = new NotificationId(idUuid);
        LocalDateTime createdAt = LocalDateTime.now();
        UserId userId = new UserId(userIdUuid);
        String message = "The quantity of the product \"3\" has been changed to \"4\".";

        Notification notification = new Notification(id, createdAt, userId, message);

        NotificationFactory notificationFactory = new NotificationFactory();
        Notification notificationToAssert =  notificationFactory.reinstantiate(id, createdAt, userId, message);

        assertNotNull(notificationToAssert);
        assertEquals(notification.getId(), notificationToAssert.getId());
        assertEquals(notification.getCreatedAt(), notificationToAssert.getCreatedAt());
        assertEquals(notification.getUserId(), notificationToAssert.getUserId());
        assertEquals(notification.getMessage(), notificationToAssert.getMessage());
    }

    @Test
    void testCreateProductStockChanged() {

        UUID userIdUuid = UUID.randomUUID();
        ProductId productId = new ProductId(3L);
        Integer stock = 4;

        UserId userId = new UserId(userIdUuid);
        String message = "The quantity of the product \"3\" has been changed to: 4.";

        Notification notification = new Notification(userId, message);

        NotificationFactory notificationFactory = new NotificationFactory();
        Notification notificationToAssert = notificationFactory.createProductStockChanged(userId, productId, stock);

        assertNotNull(notificationToAssert);
        assertNotNull(notificationToAssert.getId());
        assertNotNull(notificationToAssert.getCreatedAt());
        assertEquals(notification.getUserId(), notificationToAssert.getUserId());
        assertEquals(notification.getMessage(), notificationToAssert.getMessage());
    }
}