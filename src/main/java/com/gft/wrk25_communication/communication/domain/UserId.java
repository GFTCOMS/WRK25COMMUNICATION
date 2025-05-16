package com.gft.wrk25_communication.communication.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID id) {

    public UserId {
        Assert.notNull(id, "The User id must not be null");
    }

}
