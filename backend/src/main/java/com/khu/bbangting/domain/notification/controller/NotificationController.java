package com.khu.bbangting.domain.notification.controller;

import com.khu.bbangting.config.auth.PrincipalDetail;
import com.khu.bbangting.domain.notification.dto.NotificationsResponse;
import com.khu.bbangting.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 로그인 한 유저 sse 연결
    @GetMapping(value = "/user/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal PrincipalDetail userDetails,
                                @RequestParam(value = "lastEventId", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(userDetails.getId(), lastEventId);
    }


    // 로그인 한 유저의 모든 알림 조회
    @GetMapping("/user/notifications")
    public ResponseEntity<NotificationsResponse> notifications(@AuthenticationPrincipal PrincipalDetail userDetails) {
        return ResponseEntity.ok().body(notificationService.findAllById(userDetails.getUser().getId()));
    }

    // 알림 읽음 상태 변경
    @PatchMapping("/user/notifications/{id}")
    public ResponseEntity<?> readNotification(@PathVariable Long id) {
        notificationService.readNotification(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // 알림 삭제
    @DeleteMapping("/user/notifications/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }}
