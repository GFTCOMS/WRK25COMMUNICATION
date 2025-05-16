package com.gft.wrk25_communication.communication.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    void testUserIdReturnsId() {
        UUID id = UUID.randomUUID();
        UserId userId = new UserId(id);
        assertEquals(id, userId.id());
    }

    @Test
    void testConstructorChecksNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new UserId(null));
    }

}