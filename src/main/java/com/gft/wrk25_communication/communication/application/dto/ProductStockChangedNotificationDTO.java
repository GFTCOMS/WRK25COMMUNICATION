package com.gft.wrk25_communication.communication.application.dto;

import org.springframework.util.Assert;

public record ProductStockChangedNotificationDTO(Long productId, Integer stock) {

    public ProductStockChangedNotificationDTO {
        Assert.notNull(productId, "The product id must not be null");
        Assert.notNull(stock, "The stock must not be null");
    }

}
