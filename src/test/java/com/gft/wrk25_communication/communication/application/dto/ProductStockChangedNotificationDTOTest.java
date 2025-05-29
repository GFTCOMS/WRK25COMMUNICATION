package com.gft.wrk25_communication.communication.application.dto;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductStockChangedNotificationDTOTest {

    @Test
    void testCreateNotification() {
        Long productId = Instancio.create(Long.class);
        Integer stock = Instancio.create(Integer.class);

        ProductStockChangedNotificationDTO notification = new ProductStockChangedNotificationDTO(productId,stock);

        assertEquals(productId, notification.productId());
        assertEquals(stock, notification.stock());
    }

    @Test
    void testProductNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProductStockChangedNotificationDTO(Instancio.create(Long.class), null)
        );
        assertTrue(exception.getMessage().contains("The stock must not be null"));
    }

}
