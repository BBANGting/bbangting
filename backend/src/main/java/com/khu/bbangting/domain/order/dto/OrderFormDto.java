package com.khu.bbangting.domain.order.dto;

import com.khu.bbangting.domain.bread.dto.BreadFormDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.model.OrderStatus;
import com.khu.bbangting.domain.user.model.User;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrderFormDto { //주문하기

    private Long breadId;
    private int quantity;
    private OrderStatus orderStatus;

    @Builder
    public OrderFormDto(Long breadId, int quantity, OrderStatus orderStatus) {
        this.breadId = breadId;
        this.quantity = quantity;
        this.orderStatus = OrderStatus.ORDER;
    }

    public Order toEntity(User user, Bread bread) {
        return Order.builder()
                .user(user)
                .bread(bread)
                .quantity(quantity)
                .orderStatus(OrderStatus.ORDER)
                .build();
    }

    public static OrderFormDto fromOrder(Order order) {
        return OrderFormDto.builder()
                .breadId(order.getBread().getId())
                .quantity(order.getQuantity())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}