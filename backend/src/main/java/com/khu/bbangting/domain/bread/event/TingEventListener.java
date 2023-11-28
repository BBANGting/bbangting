package com.khu.bbangting.domain.bread.event;


import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.follow.model.Follow;
import com.khu.bbangting.domain.follow.repository.FollowRepository;
import com.khu.bbangting.domain.notification.model.Notification;
import com.khu.bbangting.domain.notification.model.NotificationType;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.notification.repository.NotificationRepository;
import com.khu.bbangting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Async
@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class TingEventListener {


    private final BreadRepository breadRepository;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void handleTingCreatedEvent(TingCreatedEvent tingCreatedEvent) {

        // 등록된 빵팅 조회
        Bread bread = breadRepository.findTingWithFollowById(tingCreatedEvent.getBread().getId());

        // 해당 빵팅 스토어를 팔로우한 유저 조회
        Set<User> followerList = new HashSet<>();
        followerList.addAll(bread.getStore().getFollowerList());

        for (User user : followerList) {
            saveNotification(bread, user, NotificationType.TING_CREATED,
                    "[" + bread.getStore().getStoreName() + "] 새로운 빵팅이 등록되었습니다.");
        }

    }

    @EventListener
    public void handleTingUpdatedEvent(TingUpdatedEvent tingUpdatedEvent) {

        // 업데이트된 빵팅 조회
        Bread bread = breadRepository.findTingWithFollowById(tingUpdatedEvent.getBread().getId());

        // 해당 빵팅 스토어를 팔로우한 유저 조회
        Set<User> followerList = new HashSet<>();
        followerList.addAll(bread.getStore().getFollowerList());

        for (User user : followerList) {
            saveNotification(bread, user, NotificationType.TING_UPDATED, tingUpdatedEvent.getMessage());
        }

    }

    private void saveNotification(Bread bread, User user, NotificationType notificationType, String message) {
        notificationRepository.save(Notification.from(bread.getStore().getStoreName(), message,
                false, LocalDateTime.now(), user, notificationType));
    }
}
