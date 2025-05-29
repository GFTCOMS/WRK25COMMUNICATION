package com.gft.wrk25_communication.communication.application.scheduled;

import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationCleanupTaskTest {

    @InjectMocks
    private NotificationCleanupTask notificationCleanupTask;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    void testDeleteOldNotifications_callsDeleteWithCorrectThreshold() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threshold = now.minusDays(25);

        notificationCleanupTask.deleteOldNotifications();

        verify(notificationRepository, times(1)).deleteByCreateAtBefore(threshold);
    }
}