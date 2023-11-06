package com.khu.bbangting.dto;

import com.khu.bbangting.model.Order;
import lombok.Data;

@Data
public class OrderDto { //주문할 상품의 정보

    public OrderDto(Order order) {
        this.userId = order.getUser().getId();
        this.breadId = order.getId();
        this.quantity = order.getQuantity();
    }

    private Long userId;
    private Long breadId;
    private int quantity;
}

