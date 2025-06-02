package com.gft.wrk25_communication.communication.application.dto;

import java.util.UUID;

public record CartProductChangedDTO(UUID userId, Long productId) {}
