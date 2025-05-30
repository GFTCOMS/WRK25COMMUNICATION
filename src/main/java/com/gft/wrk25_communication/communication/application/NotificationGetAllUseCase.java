package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationGetAllUseCase {

    private final NotificationRepository notificationRepository;

    public List<NotificationDTO> execute(UserId userId) {
        log.info("Executing use case NotificationGetAllUseCase for userId={}", userId.userId());

       List<NotificationDTO> notifications = notificationRepository.findAllByUserId(userId)
               .stream()
               .map(notification ->
                new NotificationDTO(
                        notification.getId().id(),
                        notification.getCreatedAt(),
                        notification.getUserId().userId(),
                        notification.getMessage(),
                        notification.isImportant()
                )).toList();
       log.info("Notifications found for the user {}: {}", userId.userId(), notifications.size());
       return notifications;
    }

}
