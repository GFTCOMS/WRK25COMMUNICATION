package com.gft.wrk25_communication.communication.application.dto;


import org.springframework.util.Assert;

import java.util.UUID;

public record LowStockNotification(UUID userId, Long productId,Integer quantity) {

    public LowStockNotification {
        Assert.notNull(userId, "The user id must not be null");
        Assert.notNull(productId, "The product id must not be null");
        Assert.notNull(quantity, "The quantity must not be null");
    }

}
