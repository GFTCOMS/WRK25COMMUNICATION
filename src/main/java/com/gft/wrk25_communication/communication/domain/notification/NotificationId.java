package com.gft.wrk25_communication.communication.domain.notification;

import org.springframework.util.Assert;

import java.util.UUID;

public record NotificationId(UUID id) {

    public NotificationId() {
        this(UUID.randomUUID());
    }

    public NotificationId {
        Assert.notNull(id, "id cannot be null");
    }

}
