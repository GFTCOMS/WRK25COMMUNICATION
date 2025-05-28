package com.gft.wrk25_communication.communication.application.dto;

import org.springframework.util.Assert;

public record ProductStockChangedNotificationDTO(Long id, Integer stock) {

    public ProductStockChangedNotificationDTO {
        Assert.notNull(id, "The product id must not be null");
        Assert.notNull(stock, "The stock must not be null");
    }

}
