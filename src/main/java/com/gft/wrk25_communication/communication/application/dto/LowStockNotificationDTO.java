package com.gft.wrk25_communication.communication.application.dto;

import org.springframework.util.Assert;

import java.util.UUID;

public record LowStockNotificationDTO(Long productId, Integer stock) {

    public LowStockNotificationDTO {
        Assert.notNull(productId, "The product id must not be null");
        Assert.notNull(stock, "The stock must not be null");
    }

}
