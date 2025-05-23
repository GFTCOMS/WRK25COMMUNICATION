package com.gft.wrk25_communication.communication.infrastructure.repository;

import com.gft.wrk25_communication.communication.infrastructure.entity.NotificationEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NotificationEntityRepositoryTest {

    @Autowired
    private NotificationEntityRepository repositoryToTest;
    @Autowired
    private EntityManager entityManager;
    private UUID notificationId;
    @Test
    void setImportantTrueWhereId() {

        NotificationEntity entityToUpdate = buildNotificationEntity(false);

        entityManager.persist(entityToUpdate);
        entityManager.flush();

        entityManager.refresh(entityToUpdate);
        notificationId = entityToUpdate.getId();

        repositoryToTest.setImportant(notificationId, true);
        entityManager.refresh(entityToUpdate);

        NotificationEntity entityUpdated = entityManager.find(NotificationEntity.class, notificationId);
        assertTrue(entityUpdated.isImportant());
    }

    @Test
    void setImportantFalseWhereId() {

        NotificationEntity entityToUpdate = buildNotificationEntity(true);


        entityManager.persist(entityToUpdate);
        entityManager.flush();

        entityManager.refresh(entityToUpdate);
        notificationId = entityToUpdate.getId();

        repositoryToTest.setImportant(notificationId, false);
        entityManager.refresh(entityToUpdate);

        NotificationEntity entityUpdated = entityManager.find(NotificationEntity.class, notificationId);
        assertFalse(entityUpdated.isImportant());
    }

    private NotificationEntity buildNotificationEntity(boolean important) {
        return NotificationEntity.builder()
                .id(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .userId(UUID.randomUUID())
                .message("message")
                .important(important)
                .build();
    }

}