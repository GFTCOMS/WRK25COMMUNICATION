package com.gft.wrk25_communication.communication.infrastructure.repository;

import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.infrastructure.entity.NotificationEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryImplTest {

    private List<Notification> notifications;
    private List<NotificationEntity> notificationEntities;
    private final NotificationFactory factory = new NotificationFactory();

    @Mock
    private NotificationEntityRepository notificationEntityRepository;

    @Mock
    private NotificationFactory notificationFactory;

    @InjectMocks
    private NotificationRepositoryImpl repositoryToTest;

    @BeforeEach
    void setUp() {
        initObjects();
    }

    @Test
    void testFindAllByUserId() {

        UUID userId = notificationEntities.get(0).getUserId();

        when(notificationEntityRepository.findAllByUserIdOrderByImportantDesc(userId)).thenReturn(notificationEntities);

        for (int i = 0; i < notificationEntities.size(); i++) {
            when(notificationFactory.reinstantiate(
                    new NotificationId(notificationEntities.get(i).getId()),
                    notificationEntities.get(i).getCreatedAt(),
                    new UserId(notificationEntities.get(i).getUserId()),
                    notificationEntities.get(i).getMessage(),
                    notificationEntities.get(i).isImportant()
            )).thenReturn(notifications.get(i));
        }

        List<Notification> actualNotifications = repositoryToTest.findAllByUserId(new UserId(userId));

        assertEquals(notificationEntities.size(), actualNotifications.size());
        assertTrue(actualNotifications.containsAll(notifications));
    }

    @Test
    void testSave() {

        NotificationEntity entity = notificationEntities.get(0);
        Notification notification = notifications.get(0);

        when(notificationEntityRepository.save(any(NotificationEntity.class))).thenReturn(entity);
        when(notificationFactory.reinstantiate(
                any(NotificationId.class),
                any(LocalDateTime.class),
                any(UserId.class),
                anyString(),
                anyBoolean()
        )).thenReturn(notification);

        Notification actualNotification = repositoryToTest.save(notification);

        assertEquals(notification.getId(), actualNotification.getId());
        assertEquals(notification.getCreatedAt(), actualNotification.getCreatedAt());
        assertEquals(notification.getUserId(), actualNotification.getUserId());
        assertEquals(notification.getMessage(), actualNotification.getMessage());
        assertEquals(notification.isImportant(), actualNotification.isImportant());
    }

    @Test
    void testDeleteById() {
        repositoryToTest.deleteById(notifications.get(0).getId());
        verify(notificationEntityRepository, times(1)).deleteById(notificationEntities.get(0).getId());
    }

    @Test
    void testExistsById() {

        NotificationId notificationId = new NotificationId(notificationEntities.get(0).getId());

        when(notificationEntityRepository.existsById(notificationId.id())).thenReturn(true);

        boolean response = repositoryToTest.existsById(notificationId);

        verify(notificationEntityRepository, times(1)).existsById(notificationEntities.get(0).getId());
        assertTrue(response);
    }

    @Test
    void testDeleteAllByUserId() {
        repositoryToTest.deleteAllByUserId(notifications.get(0).getUserId());
        verify(notificationEntityRepository, times(1)).deleteAllByUserId(notificationEntities.get(0).getUserId());
    }

    @Test
    void testSetAsImportant() {
        repositoryToTest.setImportant(notifications.get(0).getId(), true);
        verify(notificationEntityRepository, times(1)).setImportant(notificationEntities.get(0).getId(), true);
    }

    @Test
    void testSetAsNotImportant() {
        repositoryToTest.setImportant(notifications.get(0).getId(), false);
        verify(notificationEntityRepository, times(1)).setImportant(notificationEntities.get(0).getId(), false);
    }

    @Test
    void testDeleteOldNotifications() {
        repositoryToTest.deleteOldNotifications();
        verify(notificationEntityRepository, times(1)).deleteOldNotifications();
    }


    private void initObjects() {
        UUID userId = UUID.randomUUID();
        notificationEntities = new LinkedList<>();

        notificationEntities.addAll(
                Instancio.of(NotificationEntity.class)
                        .stream()
                        .limit(10)
                        .peek(entity -> entity.setUserId(userId))
                        .toList()
        );

        notifications = notificationEntities.stream()
                .map(entity -> factory.reinstantiate(
                        new NotificationId(entity.getId()),
                        entity.getCreatedAt(),
                        new UserId(entity.getUserId()),
                        entity.getMessage(),
                        entity.isImportant()
                ))
                .toList();
    }

}