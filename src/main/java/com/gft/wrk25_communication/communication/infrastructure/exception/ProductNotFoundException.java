package com.gft.wrk25_communication.communication.infrastructure.exception;

import com.gft.wrk25_communication.communication.domain.ProductId;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(ProductId productId) {
        super("The product " + productId.id() + " was not found");
    }

}
