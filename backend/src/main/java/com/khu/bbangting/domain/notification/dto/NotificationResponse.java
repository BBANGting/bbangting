//package com.khu.bbangting.domain.notification.dto;
//
//import com.khu.bbangting.domain.notification.model.Notification;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Getter
//@NoArgsConstructor
//public class NotificationResponse {
//
//    private Long id;
//
//    private String title;
//
//    private String message;
//
//    private Integer[] createdAt;
//
//    private boolean isRead;
//
//    @Builder
//    public NotificationResponse(Long id, String title, String message, LocalDateTime createdAt, boolean isRead) {
//        this.id = id;
//        this.title = title;
//        this.message = message;
//        this.isRead = isRead;
//    }
//
//    public static NotificationResponse from(Notification notification) {
//        return NotificationResponse.builder()
//                .id(notification.getId())
//                .title(notification.getTitle())
//                .message(notification.getMessage())
//                .createdAt(notification.getCreatedAt())
//                .isRead(notification.isRead())
//                .build();
//    }
//}
