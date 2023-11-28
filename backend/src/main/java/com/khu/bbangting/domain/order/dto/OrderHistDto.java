package com.khu.bbangting.domain.order.dto;

import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto { //주문 목록 불러오기

    private Long orderId;

    private int quantity;

    private Timestamp orderDate;

    private OrderStatus orderStatus;

    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.quantity = order.getQuantity();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getOrderStatus();
    }

    private List<OrderHistDto> orderList = new ArrayList<>();

    //주문내역 리스트
    public void addOrderHistDto(OrderHistDto orderHistDto) {
        orderList.add(orderHistDto);
    }

}
