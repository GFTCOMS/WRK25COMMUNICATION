package com.gft.wrk25_communication.communication.infrastructure.controller.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NotificationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationFactory factory;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private Notification notification;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        notification = factory.reinstantiate(
                new NotificationId(UUID.randomUUID()),
                LocalDateTime.now(),
                new UserId(userId),
                "Test message",
                false
        );
        repository.save(notification);
    }

    @Test
    void getAllNotificationsFromUserId() throws Exception {
        mockMvc.perform(get("/api/v1/notifications/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(notification.getId().id().toString()))
                .andExpect(jsonPath("$[0].userId").value(userId.toString()))
                .andExpect(jsonPath("$[0].message").value("Test message"))
                .andExpect(jsonPath("$[0].important").value(false));
    }

    @Test
    void updateNotificationImportant() throws Exception {
        NotificationDTO dto = new NotificationDTO(
                notification.getId().id(),
                notification.getCreatedAt(),
                userId,
                "Updated",
                true
        );

        mockMvc.perform(patch("/api/v1/notifications/{id}", notification.getId().id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        // Verifica el cambio
        mockMvc.perform(get("/api/v1/notifications/{userId}", userId))
                .andExpect(jsonPath("$[0].important").value(true));
    }


    @Test
    void deleteNotification() throws Exception {
        mockMvc.perform(delete("/api/v1/notifications/{id}", notification.getId().id()))
                .andExpect(status().isNoContent());
    }
}
