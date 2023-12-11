package com.khu.bbangting.domain.notification.controller;

import com.khu.bbangting.domain.notification.dto.NotificationsResponse;
import com.khu.bbangting.domain.notification.service.NotificationService;
import com.khu.bbangting.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 로그인 한 유저 sse 연결
    @GetMapping(value = "/user/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal User user,
                                @RequestParam(value = "lastEventId", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(user.getId(), lastEventId);
    }


    // 로그인 한 유저의 모든 알림 조회
    @GetMapping("/notifications")
    public ResponseEntity<NotificationsResponse> notifications(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(notificationService.findAllById(user.getId()));
    }

    // 알림 읽음 상태 변경
    @PatchMapping("/notifications/{id}")
    public ResponseEntity<?> readNotification(@PathVariable Long id) {
        notificationService.readNotification(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // 알림 삭제
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }}
