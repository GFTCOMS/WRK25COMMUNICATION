package com.gft.wrk25_communication.communication.domain.notification;

import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.domain.OrderId;
import com.gft.wrk25_communication.communication.domain.UserId;
import org.instancio.Instancio;
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
        boolean important = false;

        Notification notification = new Notification(id, createdAt, userId, message, important);

        NotificationFactory notificationFactory = new NotificationFactory();
        Notification notificationToAssert = notificationFactory.reinstantiate(id, createdAt, userId, message, important);

        assertNotNull(notificationToAssert);
        assertEquals(notification.getId(), notificationToAssert.getId());
        assertEquals(notification.getCreatedAt(), notificationToAssert.getCreatedAt());
        assertEquals(notification.getUserId(), notificationToAssert.getUserId());
        assertEquals(notification.getMessage(), notificationToAssert.getMessage());
    }

    @Test
    void testCreateProductStockChanged() {

        UserId userId = new UserId(UUID.randomUUID());
        ProductDTO productDTO = Instancio.create(ProductDTO.class);

        String message = "The stock of the product \"" + productDTO.name() + "\" is lower than: "+ productDTO.inventoryData().stock() + ".";

        Notification notification = new Notification(userId, message);

        NotificationFactory notificationFactory = new NotificationFactory();

        Notification notificationToAssert = notificationFactory.createLowStockNotification(userId, productDTO);

        assertNotNull(notificationToAssert);
        assertNotNull(notificationToAssert.getId());
        assertNotNull(notificationToAssert.getCreatedAt());
        assertEquals(notification.getUserId(), notificationToAssert.getUserId());
        assertEquals(notification.getMessage(), notificationToAssert.getMessage());
    }

    @Test
    void testCreateOrderStatusChanged() {

        UserId userId = new UserId(UUID.randomUUID());
        OrderId orderId = new OrderId(UUID.randomUUID());
        String message = String.format("The state of the order \"%s\" has changed to %s.", orderId.id(), "IN DELIVERY");

        Notification notification = new Notification(userId, message);

        NotificationFactory notificationFactory = new NotificationFactory();
        Notification notificationToAssert = notificationFactory.createOrderStatusChangedNotification(userId, orderId, "IN_DELIVERY");

        assertNotNull(notificationToAssert);
        assertNotNull(notificationToAssert.getId());
        assertNotNull(notificationToAssert.getCreatedAt());
        assertEquals(notification.getUserId(), notificationToAssert.getUserId());
        assertEquals(message, notificationToAssert.getMessage());
    }

    @Test
    void testCreateProductChangedNotification() {

        UserId userId = new UserId(UUID.randomUUID());
        ProductDTO productDTO = Instancio.create(ProductDTO.class);

        String message = String.format("The product \"%s\" has changed the stock to %s.", productDTO.name(), productDTO.inventoryData().stock());

        Notification notification = new Notification(userId, message);

        NotificationFactory notificationFactory = new NotificationFactory();
        Notification notificationToAssert = notificationFactory.createProductChangedNotification(userId, productDTO);

        assertNotNull(notificationToAssert);
        assertNotNull(notificationToAssert.getId());
        assertNotNull(notificationToAssert.getCreatedAt());
        assertEquals(notification.getUserId(), notificationToAssert.getUserId());
        assertEquals(message, notificationToAssert.getMessage());
    }
}