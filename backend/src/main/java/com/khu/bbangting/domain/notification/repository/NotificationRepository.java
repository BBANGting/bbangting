package com.khu.bbangting.domain.notification.repository;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.notification.model.Notification;
import com.khu.bbangting.domain.user.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    long countByUserAndChecked(User user, boolean checked);

}
