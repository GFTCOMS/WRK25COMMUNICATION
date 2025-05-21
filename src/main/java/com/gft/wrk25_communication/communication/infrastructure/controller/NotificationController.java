package com.gft.wrk25_communication.communication.infrastructure.controller;

import com.gft.wrk25_communication.communication.application.*;
import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationGetAllUseCase notificationGetAllUseCase;
    private final NotificationDeleteByIdUseCase notificationDeleteByIdUseCase;
    private final NotificationExistsByIdUseCase notificationExistsByIdUseCase;
    private final NotificationSetImportantByIdUseCase notificationSetImportantByIdUseCase;
    private final NotificationSetNotImportantByIdUseCase notificationSetNotImportantByIdUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsFromUserId(@PathVariable String userId) {
        return ResponseEntity.ok(notificationGetAllUseCase.execute(new UserId(UUID.fromString(userId))));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateNotification(@PathVariable("id") UUID id, @RequestBody NotificationDTO notificationDTO) {

        NotificationId notificationId = new NotificationId(id);

        if (!notificationExistsByIdUseCase.execute(notificationId)) {
            return ResponseEntity.notFound().build();
        }

        if (notificationDTO.important()) {
            notificationSetImportantByIdUseCase.execute(notificationId);
        }

        if (!notificationDTO.important()) {
            notificationSetNotImportantByIdUseCase.execute(notificationId);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNotification(@PathVariable("id") UUID id) {

        NotificationId notificationId = new NotificationId(id);

        if(notificationExistsByIdUseCase.execute(notificationId)) {
            notificationDeleteByIdUseCase.execute(notificationId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
