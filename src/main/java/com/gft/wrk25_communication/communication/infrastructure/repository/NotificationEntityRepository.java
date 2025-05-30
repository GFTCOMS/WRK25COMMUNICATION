package com.gft.wrk25_communication.communication.infrastructure.repository;

import com.gft.wrk25_communication.communication.infrastructure.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, UUID> {

    List<NotificationEntity> findAllByUserId(UUID userId);
    void deleteAllByUserId(UUID userId);

    @Modifying
    @Query("UPDATE NotificationEntity n SET n.important = :important WHERE n.id = :id")
    void setImportant(UUID id, boolean important);

    @Modifying
    @Query(value = "DELETE FROM notifications WHERE created_at < DATEADD('DAY', -25, CURRENT_DATE)", nativeQuery = true)
    void deleteOldNotifications();;
}
