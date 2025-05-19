package com.gft.wrk25_communication.communication.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationDTO(UUID id, LocalDateTime createdAt, UUID userId, String message, boolean important) {}