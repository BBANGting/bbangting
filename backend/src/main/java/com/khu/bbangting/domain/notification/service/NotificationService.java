package com.khu.bbangting.domain.notification.service;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.notification.model.Notification;
import com.khu.bbangting.domain.notification.repository.NotificationRepository;
import com.khu.bbangting.domain.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // 빵팅 등록 알림
    @Transactional
    public void createTing(User user, Bread bread) {
        Notification notification = createNotification(user, bread);
        notificationRepository.save(notification);

    }

    private Notification createNotification(User user, Bread bread) {
        return Notification.builder()
                .user(user)
                .title("새로운 빵팅!!!")
                .message("[" + bread.getStore().getStoreName() + "] 새로운 빵팅은 " + bread.getBreadName() + "입니다.")
                .isRead(false)
                .build();
    }

    // 오늘의 빵팅 알림
    @Transactional
    public void openTing(User user, Bread bread) {
        Notification notification = openNotification(user, bread);
        notificationRepository.save(notification);

    }

    private Notification openNotification(User user, Bread bread) {
        return Notification.builder()
                .user(user)
                .title("오늘의 빵팅!!!")
                .message("[" + bread.getStore().getStoreName() + "] 오늘의 빵은 " + bread.getBreadName() + "입니다.")
                .isRead(false)
                .build();
    }

    // 빵팅 재오픈 알림
    public void reOpenTing(User user, Bread bread) {
        Notification notification = reOpenNotification(user, bread);
        notificationRepository.save(notification);
    }

    private Notification reOpenNotification(User user, Bread bread) {
        return Notification.builder()
                .user(user)
                .title("[" + bread.getStore().getStoreName() + "] 재오픈!!!")
                .message("[" + bread.getStore().getStoreName() + "] " + bread.getBreadName() + " 빵팅이 재오픈 되었습니다.")
                .isRead(false)
                .build();
    }

    @Transactional
    public void readNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 알림입니다."));
        notification.read();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteNotification() {
        log.info("<----deleteNotification 스케줄러 작동 시작");
        List<Notification> isReadNotificationList = notificationRepository.findAllByIsRead(true);

        for (Notification notification : isReadNotificationList) {
            notificationRepository.deleteById(notification.getId());
        }
        log.info("deleteNotification 스케줄러 작동 완료---->\n");

    }
}
