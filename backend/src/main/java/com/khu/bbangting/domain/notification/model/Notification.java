package com.khu.bbangting.domain.notification.model;

import com.khu.bbangting.domain.user.model.User;
import jakarta.persistence.*;
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
    @Column(name = "notiId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //임의 알림 기능 추가
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    private boolean checked;

    @CreationTimestamp
    private LocalDateTime createdTime;

//    @CreationTimestamp
//    private Timestamp lastReadTime;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public static Notification from(String title, String message, boolean checked, LocalDateTime createdTime, User user, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.title = title;
        notification.message = message;
        notification.checked = checked;
        notification.createdTime = createdTime;
        notification.user = user;
        notification.notificationType = notificationType;

        return notification;
    }

    public void read() {
        this.checked = true;
    }

}
