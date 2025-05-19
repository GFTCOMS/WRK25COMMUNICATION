package com.gft.wrk25_communication.communication.domain.notification;

import com.gft.wrk25_communication.communication.domain.UserId;

import java.util.List;
import java.util.Optional;


public interface NotificationRepository {

    List<Notification> findAllByUserId(UserId id);

    Optional<Notification> findById(NotificationId id);

    Optional<Notification> save(Notification notification);

    void deleteById(NotificationId id);

}
