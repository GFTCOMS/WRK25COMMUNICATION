package com.gft.wrk25_communication.communication.infrastructure.controller.it;

import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.infrastructure.repository.NotificationRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(NotificationRepositoryImpl.class)
class NotificationRepositoryIT {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public NotificationFactory notificationFactory() {
            return new NotificationFactory();
        }
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NotificationRepositoryImpl repository;

    @Autowired
    private NotificationFactory notificationFactory;

    private UserId userId;
    private NotificationId notificationId;
    private LocalDateTime createdAt;
    private Notification notification;

    @BeforeEach
    void initData() {
        UUID userUuid = UUID.randomUUID();
        userId = new UserId(userUuid);
        UUID notificationUuid = UUID.randomUUID();
        notificationId = new NotificationId(notificationUuid);
        createdAt = Instancio.create(LocalDateTime.class);
        notification = notificationFactory.reinstantiate(notificationId, createdAt, userId, "Mensaje test", true);
    }

    @Test
    void testSave() {
        Notification saved = repository.save(notification);
        assertNotNull(saved);
        assertEquals(notification.getId(), saved.getId());
    }

    @Test
    void testFindAllByUserId() {
        repository.save(notification);
        List<Notification> notifications = repository.findAllByUserId(userId);
        boolean found = notifications.stream().anyMatch(n -> n.getId().equals(notificationId));
        assertTrue(found);
    }

    @Test
    void testExistsById() {
        repository.save(notification);
        boolean exists = repository.existsById(notificationId);
        assertTrue(exists);
    }

    @Test
    void testDeleteById() {
        repository.save(notification);
        repository.deleteById(notificationId);
        boolean exists = repository.existsById(notificationId);
        assertFalse(exists);
    }

    @Test
    void testSetAsImportant() {
        UUID notificationUuidNotImportant = UUID.randomUUID();
        NotificationId notificationIdNotImportant = new NotificationId(notificationUuidNotImportant);
        Notification notImportant = notificationFactory.reinstantiate(notificationIdNotImportant, createdAt, userId, "Msg", false);

        repository.save(notImportant);
        repository.setImportant(notificationIdNotImportant, true);

        entityManager.flush();
        entityManager.clear();

        Notification updated = repository.findAllByUserId(userId).stream()
                .filter(n -> n.getId().equals(notificationIdNotImportant))
                .findFirst()
                .orElseThrow();

        assertTrue(updated.isImportant());
    }

    @Test
    void testSetAsNotImportant() {
        UUID notificationUuidImportant = UUID.randomUUID();
        NotificationId notificationIdImportant = new NotificationId(notificationUuidImportant);
        Notification important = notificationFactory.reinstantiate(notificationIdImportant, createdAt, userId, "Msg", true);

        repository.save(important);
        repository.setImportant(notificationIdImportant,false);

        entityManager.flush();
        entityManager.clear();

        Notification updated = repository.findAllByUserId(userId).stream()
                .filter(n -> n.getId().equals(notificationIdImportant))
                .findFirst()
                .orElseThrow();

        assertFalse(updated.isImportant());
    }

}
