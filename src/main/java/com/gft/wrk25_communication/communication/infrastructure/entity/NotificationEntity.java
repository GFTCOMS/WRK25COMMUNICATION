package com.gft.wrk25_communication.communication.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    private UUID id;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    private UUID userId;
    private String message;

}
