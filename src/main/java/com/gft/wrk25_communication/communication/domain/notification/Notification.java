package com.gft.wrk25_communication.communication.domain.notification;

import com.gft.wrk25_communication.communication.domain.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Notification {

    private final NotificationId id;
    private final LocalDateTime createdAt;
    private final UserId userId;
    private final String message;
    private final boolean important;

    Notification(UserId userId, String message) {
        this.id = new NotificationId();
        this.createdAt = LocalDateTime.now();
        this.userId = userId;
        Assert.notNull(message, "\"message\" must not be null");
        this.message = message;
        this.important = false;
    }

    Notification(NotificationId id, LocalDateTime createdAt, UserId userId, String message, boolean important) {
        Assert.notNull(id, "\"id\" must not be null");
        Assert.notNull(createdAt, "\"createdAt\" must not be null");
        Assert.notNull(userId, "\"userId\" must not be null");
        Assert.notNull(message, "\"message\" must not be null");
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.message = message;
        this.important = important;
    }

}
