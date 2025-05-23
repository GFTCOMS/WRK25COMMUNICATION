package com.gft.wrk25_communication.communication.infrastructure.controller.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.wrk25_communication.communication.application.*;
import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.infrastructure.controller.NotificationController;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc
class NotificationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationGetAllUseCase notificationGetAllUseCase;

    @MockitoBean
    private NotificationDeleteByIdUseCase notificationDeleteByIdUseCase;

    @MockitoBean
    private NotificationSetImportantByIdUseCase notificationSetImportantByIdUseCase;



    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllNotificationsFromUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();
        LocalDateTime createdAt = Instancio.create(LocalDateTime.class);

        NotificationDTO notificationDTO = new NotificationDTO(
                notificationId,
                createdAt,
                userId,
                "Test message",
                true
        );

        Mockito.when(notificationGetAllUseCase.execute(any()))
                .thenReturn(List.of(notificationDTO));

        mockMvc.perform(get("/api/v1/notifications/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(notificationId.toString()))
                .andExpect(jsonPath("$[0].userId").value(userId.toString()))
                .andExpect(jsonPath("$[0].message").value("Test message"))
                .andExpect(jsonPath("$[0].important").value(true));
    }

    @Test
    void updateNotificationImportant() throws Exception {
        UUID notificationId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = Instancio.create(LocalDateTime.class);

        NotificationDTO dto = new NotificationDTO(notificationId, createdAt, userId, "Updated", true);


        mockMvc.perform(patch("/api/v1/notifications/{id}", notificationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        Mockito.verify(notificationSetImportantByIdUseCase).execute(any(), anyBoolean());
    }


    @Test
    void deleteNotification() throws Exception {
        UUID notificationId = UUID.randomUUID();


        mockMvc.perform(delete("/api/v1/notifications/{id}", notificationId))
                .andExpect(status().isNoContent());

        Mockito.verify(notificationDeleteByIdUseCase).execute(any());
    }


}
