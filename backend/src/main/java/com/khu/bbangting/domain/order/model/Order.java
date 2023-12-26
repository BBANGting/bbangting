package com.khu.bbangting.domain.order.model;

import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.bread.model.Bread;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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
    private Timestamp orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="breadId")
    private Bread bread;

    @Builder
    private Order(User user, Bread bread, int quantity, OrderStatus orderStatus) {
        this.user = user;
        this.bread = bread;
        this.quantity = quantity;
        this.orderStatus = OrderStatus.ORDER;
    }

    public static Order from(int quantity, OrderStatus orderStatus, User user, Bread bread) {
        Order order = new Order();
        order.quantity = quantity;
        order.orderStatus = orderStatus;
        order.user = user;
        order.bread = bread;

        return order;
    }

    public void attach(Bread bread) {
        this.bread = bread;
    }


    public void detach() {
        this.bread = null;
    }

}