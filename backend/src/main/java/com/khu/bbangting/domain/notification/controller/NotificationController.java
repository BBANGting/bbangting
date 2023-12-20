package com.khu.bbangting.domain.notification.controller;

import com.khu.bbangting.domain.notification.dto.NotificationDto;
import com.khu.bbangting.domain.notification.model.Notification;
import com.khu.bbangting.domain.notification.repository.NotificationRepository;
import com.khu.bbangting.domain.notification.service.NotificationService;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    // 로그인 한 유저의 모든 알림 조회
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDto>> notifications(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Notification> notificationList = notificationRepository.findAllByUserId(user.getId());
        List<NotificationDto> isNotReadList = notificationService.getNotification(notificationList);

        for (NotificationDto notificationDto : isNotReadList) {
            Notification notification = notificationRepository.findById(notificationDto.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOTI_NOT_FOUND));
            notificationService.readNotification(notification.getId());
        }

        return ResponseEntity.ok().body(isNotReadList);
    }

}
