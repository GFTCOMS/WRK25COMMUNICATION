package com.gft.wrk25_communication.communication.application.dto;

import org.springframework.util.Assert;

import java.util.UUID;

public record OrderStatusChangedNotificationDTO(UUID userId, UUID orderId, String orderStatus) {

    public OrderStatusChangedNotificationDTO {
        Assert.notNull(userId, "user id must not be null");
        Assert.notNull(orderId, "order id must not be null");
        Assert.notNull(orderStatus, "order state must not be null");
    }

}
