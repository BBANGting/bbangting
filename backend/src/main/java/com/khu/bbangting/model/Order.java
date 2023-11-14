package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name="orders")
public class Order {

    @Id
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @CreationTimestamp
    private LocalDateTime orderDate;

//    @Column(nullable = false)
//    private char paymentStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @OneToOne
    @JoinColumn(name="breadId")
    private Bread bread;

    @Builder
    private Order(User user, Bread bread, int quantity, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.user = user;
        this.bread = bread;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.ORDER;
        bread.removeStock(quantity);
    }

}