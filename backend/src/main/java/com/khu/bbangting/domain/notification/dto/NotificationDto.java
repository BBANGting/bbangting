package com.khu.bbangting.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
public class NotificationDto {

    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdAt;

    @Builder
    public NotificationDto(Long id, String title, String message, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }
}
