package com.gft.wrk25_communication.communication.infrastructure.controller;

import com.gft.wrk25_communication.communication.application.*;
import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@Slf4j
public class NotificationController {

    private final NotificationGetAllUseCase notificationGetAllUseCase;
    private final NotificationDeleteByIdUseCase notificationDeleteByIdUseCase;
    private final NotificationSetImportantByIdUseCase notificationSetImportantByIdUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsFromUserId(@PathVariable String userId) {
        log.info("GET /notifications/{}", userId);
        return ResponseEntity.ok(notificationGetAllUseCase.execute(new UserId(UUID.fromString(userId))));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNotification(@PathVariable("id") UUID id, @RequestBody NotificationDTO notificationDTO) {
        log.info("PATCH /notifications/{} setImportant={}", id, notificationDTO.important());
        notificationSetImportantByIdUseCase.execute(new NotificationId(id), notificationDTO.important());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable("id") UUID id) {
        log.info("DELETE /notifications/{}", id);
        notificationDeleteByIdUseCase.execute(new NotificationId(id));
    }

}
