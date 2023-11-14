package com.khu.bbangting.dto.order;

import com.khu.bbangting.dto.bread.BreadFormDto;
import com.khu.bbangting.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
public class OrderFormDto { //주문하기

    private Long userId;

    private Long breadId;

    private int quantity;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private BreadFormDto breadFormDto;

    @Builder
    public OrderFormDto(Long userId, Long breadId, int quantity, LocalDateTime orderDate, OrderStatus orderStatus, BreadFormDto breadFormDto) {
        this.userId = userId;
        this.breadId = breadId;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.ORDER;
        this.breadFormDto = breadFormDto;
    }

    public Order toEntity(User user, Bread bread) {
        return Order.builder()
                .user(user)
                .bread(bread)
                .quantity(quantity)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();
    }

    public static OrderFormDto fromOrder(Order order) {
        return OrderFormDto.builder()
                .userId(order.getUser().getId())
                .breadId(order.getBread().getId())
                .quantity(order.getQuantity())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .breadFormDto(BreadFormDto.fromBread(order.getBread()))
                .build();
    }

}