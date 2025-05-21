package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationGetAllUseCase {

    private final NotificationRepository notificationRepository;

    public List<NotificationDTO> execute(UserId userId) {
        return notificationRepository.findAllByUserId(userId).stream().map(notification ->
                new NotificationDTO(
                        notification.getId().id(),
                        notification.getCreatedAt(),
                        notification.getUserId().id(),
                        notification.getMessage(),
                        notification.isImportant()
                )).toList();
    }

}
