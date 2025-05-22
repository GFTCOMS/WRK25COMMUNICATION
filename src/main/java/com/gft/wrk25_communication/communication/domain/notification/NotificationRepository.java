package com.gft.wrk25_communication.communication.domain.notification;

import com.gft.wrk25_communication.communication.domain.UserId;

import java.util.List;

public interface NotificationRepository {

    List<Notification> findAllByUserId(UserId id);

    boolean existsById(NotificationId id);

    Notification save(Notification notification);

    void deleteById(NotificationId id);

    void deleteAllByUserId(UserId id);

    void setAsImportant(NotificationId id);

    void setAsNotImportant(NotificationId id);

}
