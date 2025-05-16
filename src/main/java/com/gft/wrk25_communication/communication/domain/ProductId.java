package com.gft.wrk25_communication.communication.domain;

import org.springframework.util.Assert;

public record ProductId(Long id) {

    public ProductId {
        Assert.notNull(id, "The product id must not be null");
    }

}
