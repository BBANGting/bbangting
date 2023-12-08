//package com.khu.bbangting.domain.notification.repository;
//
//import com.khu.bbangting.domain.notification.model.Notification;
//import com.khu.bbangting.domain.user.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    List<Notification> findAllByReceiverIdOrderByCreatedAtDesc(Long id);
//    void deleteAllByReceiver(User receiver);
//
//}
