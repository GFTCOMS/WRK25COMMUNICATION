package com.gft.wrk25_communication.communication.infrastructure.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final String error;

    public ErrorResponse(String error) {
        this.timestamp = LocalDateTime.now();
        this.error = error;
    }

}
