package com.khu.bbangting.domain.notification.service;

import com.khu.bbangting.domain.notification.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    public void markAsRead(List<Notification> notifications) {
        notifications.forEach(Notification::read); // 읽지 않은 알림 리스트를 전달받아 Entity에서 읽은 상태로 직접 바꾸기
    }
}
