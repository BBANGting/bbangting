package com.khu.bbangting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="orders")
public class Order {
    @Id
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @CreationTimestamp
    private Timestamp orderDate;

    //Entity 수정 -> 결제는 일단 구현안하기로 했으므로!
//    @Column(nullable = false)
//    private char paymentStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="breadId")
    private Bread bread;

    public static Order createOrder(User user, Bread bread, int quantity) {

        Order order = new Order();
        order.setQuantity(quantity);
        order.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));
        order.setOrderStatus(OrderStatus.ORDER);
        order.setUser(user);
        order.setBread(bread);
        bread.subStock(quantity);

        return order;
    }

    //주문 취소 상태로 변환 & 주문 수량만큼 상품 재고에 더해주기
    public void cancel() {
        this.orderStatus = OrderStatus.CANCEL;
        this.getBread().addStock(quantity);

    }
}