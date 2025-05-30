package com.gft.wrk25_communication.communication.infrastructure.repository;

import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import com.gft.wrk25_communication.communication.infrastructure.entity.NotificationEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationEntityRepository repository;
    private final NotificationFactory factory;

    @Override
    public List<Notification> findAllByUserId(UserId userId) {
        return repository.findAllByUserId(userId.userId()).stream().map(notificationEntity ->
                factory.reinstantiate(
                        new NotificationId(notificationEntity.getId()),
                        notificationEntity.getCreatedAt(),
                        new UserId(notificationEntity.getUserId()),
                        notificationEntity.getMessage(),
                        notificationEntity.isImportant()
                )).toList();
    }

    @Override
    public boolean existsById(NotificationId id) {
        return repository.existsById(id.id());
    }

    @Override
    @Transactional
    public Notification save(Notification notification) {

        NotificationEntity entityToSave =
                NotificationEntity.builder()
                        .id(notification.getId().id())
                        .createdAt(notification.getCreatedAt())
                        .userId(notification.getUserId().userId())
                        .userId(notification.getUserId().userId())
                        .message(notification.getMessage())
                        .important(notification.isImportant())
                        .build();

        NotificationEntity savedEntity = repository.save(entityToSave);

        return factory.reinstantiate(
                new NotificationId(savedEntity.getId()),
                savedEntity.getCreatedAt(),
                new UserId(savedEntity.getUserId()),
                savedEntity.getMessage(),
                savedEntity.isImportant()
        );
    }

    @Override
    @Transactional
    public void deleteById(NotificationId id) {
        repository.deleteById(id.id());
    }

    @Override
    @Transactional
    public void deleteAllByUserId(UserId userId) {
        repository.deleteAllByUserId(userId.userId());
    }

    @Override
    @Transactional
    public void setImportant(NotificationId id, boolean important) {
        repository.setImportant(id.id(), important);
    }

    @Transactional
    @Override
    public void deleteOldNotifications() {
        repository.deleteOldNotifications();
    }

}
