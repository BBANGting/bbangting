package com.khu.bbangting.domain.notification.repository;

import com.khu.bbangting.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByIdOrderByCreatedAtDesc(Long id);

}
