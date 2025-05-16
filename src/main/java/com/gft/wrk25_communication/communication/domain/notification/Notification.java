package com.gft.wrk25_communication.communication.domain.notification;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.gft.wrk25_communication.communication.domain.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode

public class Notification {

    @JsonUnwrapped
    private final NotificationId id;
    private final LocalDateTime createdAt;
    private final UserId userId;
    private final String message;

    Notification(UserId userId, String message) {
        this.id = new NotificationId();
        this.createdAt = LocalDateTime.now();
        this.userId = userId;
        Assert.notNull(message, "\"message\" must not be null");
        this.message = message;
    }

    Notification(NotificationId id, LocalDateTime createdAt, UserId userId, String message) {
        Assert.notNull(id, "\"id\" must not be null");
        Assert.notNull(createdAt, "\"createdAt\" must not be null");
        Assert.notNull(userId, "\"userId\" must not be null");
        Assert.notNull(message, "\"message\" must not be null");
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.message = message;
    }

}
