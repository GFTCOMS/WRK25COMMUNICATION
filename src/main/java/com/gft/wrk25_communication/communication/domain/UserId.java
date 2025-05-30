package com.gft.wrk25_communication.communication.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID userId) {

    public UserId {
        Assert.notNull(userId, "The user id must not be null");
    }

}
