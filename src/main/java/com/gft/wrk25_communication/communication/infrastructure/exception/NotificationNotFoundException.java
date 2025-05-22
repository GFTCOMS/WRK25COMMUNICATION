package com.gft.wrk25_communication.communication.infrastructure.exception;

import com.gft.wrk25_communication.communication.domain.notification.NotificationId;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(NotificationId id) {
        super("Notification with id " + id + " not found");
    }
}
