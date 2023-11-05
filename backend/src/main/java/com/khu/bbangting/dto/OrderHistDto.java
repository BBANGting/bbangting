package com.khu.bbangting.dto;

import com.khu.bbangting.model.OrderStatus;
import lombok.Data;

@Data
public class OrderHistDto { //주문 내역에 담을 정보

    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;
}
