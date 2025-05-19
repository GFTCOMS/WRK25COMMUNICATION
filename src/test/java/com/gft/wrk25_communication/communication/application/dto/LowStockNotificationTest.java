package com.gft.wrk25_communication.communication.application.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LowStockNotificationTest {

    @Test
    void shouldCreateNotificationWithValidData() {
        UUID userId = UUID.randomUUID();
        Long productId = 123L;
        Integer quantity = 10;

        LowStockNotification notification = new LowStockNotification(userId, productId, quantity);

        assertEquals(userId, notification.userId());
        assertEquals(productId, notification.productId());
        assertEquals(quantity, notification.quantity());
    }

    @Test
    void shouldFailWhenUserIdIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new LowStockNotification(null, 123L, 10)
        );
        assertTrue(exception.getMessage().contains("The user id must not be null"));
    }

    @Test
    void shouldFailWhenProductIdIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new LowStockNotification(UUID.randomUUID(), null, 10)
        );
        assertTrue(exception.getMessage().contains("The product id must not be null"));
    }

    @Test
    void shouldFailWhenStockIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new LowStockNotification(UUID.randomUUID(), 123L, null)
        );
        assertTrue(exception.getMessage().contains("The quantity must not be null"));
    }
}
