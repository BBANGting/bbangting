package com.khu.bbangting.domain.notification.model;

import com.khu.bbangting.domain.user.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
@Getter @Setter
public class Notification {

    @Id
    @Column(name = "notificationId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //임의 알림 기능 추가
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    private boolean isRead;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @Builder
    public Notification(User user, String title, String message, boolean isRead, LocalDateTime createdAt) {
        this.user = user;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public void read() {
        this.isRead = true;
    }

}