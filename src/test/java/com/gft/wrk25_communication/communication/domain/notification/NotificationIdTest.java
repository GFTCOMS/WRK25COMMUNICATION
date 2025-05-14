package com.gft.wrk25_communication.communication.domain.notification;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NotificationIdTest {

    @Test
    void testEmptyConstructorReturnsValidUUID() {
        NotificationId notificationId = new NotificationId();
        assertNotNull(notificationId.id());
    }

    @Test
    void testConstructorReturnsUUID() {
        UUID uuid = UUID.randomUUID();
        NotificationId notificationId = new NotificationId(uuid);
        assertEquals(uuid, notificationId.id());
    }

    @Test
    void testConstructorChecksNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new NotificationId(null));
    }
}