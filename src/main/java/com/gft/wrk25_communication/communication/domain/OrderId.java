package com.gft.wrk25_communication.communication.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record OrderId(UUID id) {

    public OrderId {
        Assert.notNull(id, "The order id must not be null");
    }

}
