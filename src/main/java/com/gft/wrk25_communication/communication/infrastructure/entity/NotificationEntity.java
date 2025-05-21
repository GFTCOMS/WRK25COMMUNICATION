package com.gft.wrk25_communication.communication.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
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
    private boolean important;

}
