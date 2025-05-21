package com.gft.wrk25_communication.communication.application.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LowStockNotificationDTOTest {

    @Test
    void testCreateNotification() {
        UUID userId = UUID.randomUUID();
        Long productId = 123L;
        Integer quantity = 10;

        LowStockNotificationDTO notification = new LowStockNotificationDTO(userId, productId, quantity);

        assertEquals(userId, notification.userId());
        assertEquals(productId, notification.productId());
        assertEquals(quantity, notification.quantity());
    }

    @Test
    void testIdNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new LowStockNotificationDTO(null, 123L, 10)
        );
        assertTrue(exception.getMessage().contains("The user id must not be null"));
    }

    @Test
    void testProductIdNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new LowStockNotificationDTO(UUID.randomUUID(), null, 10)
        );
        assertTrue(exception.getMessage().contains("The product id must not be null"));
    }

    @Test
    void testQuantityNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new LowStockNotificationDTO(UUID.randomUUID(), 123L, null)
        );
        assertTrue(exception.getMessage().contains("The quantity must not be null"));
    }
}
