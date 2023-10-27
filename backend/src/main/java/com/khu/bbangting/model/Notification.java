package com.khu.bbangting.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @Column(name = "notiId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private Timestamp createdTime;

    @CreationTimestamp
    private Timestamp lastReadTime;

    @ManyToOne
    @JoinColumn(name="userId")
    private User users;

}
