package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Order {

    @Id
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @OneToOne
    @JoinColumn(name="breadId")
    private Bread bread;

    @Column(nullable = false)
    private int quantity;

    @CreationTimestamp
    private Timestamp orderDate;

    @Column(nullable = false)
    private char paymentStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}