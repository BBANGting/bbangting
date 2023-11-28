package com.khu.bbangting.domain.order.dto;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.model.OrderStatus;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistDto { //주문 목록 불러오기

    private Long orderId;
    private BreadInfoDto breadInfoDto;
    private int quantity;
    private Timestamp orderDate;
    private OrderStatus orderStatus;

    public static OrderHistDto fromOrder(Order order) {
        return OrderHistDto.builder()
                .orderId(order.getId())
                .breadInfoDto(BreadInfoDto.fromBread(order.getBread()))
                .quantity(order.getQuantity())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
