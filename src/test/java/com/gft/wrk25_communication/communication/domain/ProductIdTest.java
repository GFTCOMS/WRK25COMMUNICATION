package com.gft.wrk25_communication.communication.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    @Test
    void testConstructorReturnsUUID() {
        Long id = 7L;
        ProductId productId = new ProductId(id);
        assertEquals(id, productId.id());
    }

    @Test
    void testConstructorChecksNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new ProductId(null));
    }

}