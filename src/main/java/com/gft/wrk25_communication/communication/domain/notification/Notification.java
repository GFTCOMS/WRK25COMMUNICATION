package com.gft.wrk25_communication.communication.domain.notification;

import lombok.Generated;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
public abstract class Notification {

    private final NotificationId id;
    private final LocalDateTime createdAt;

    @Generated
    protected Notification() {
        this.id = new NotificationId();
        this.createdAt = LocalDateTime.now();
    }

    @Generated
    protected Notification(NotificationId id, LocalDateTime createdAt) {
       Assert.notNull(id, "Notification id must not be null");
       Assert.notNull(createdAt, "Notification createdAt must not be null");
       this.id = id;
       this.createdAt = createdAt;
    }

}
