package com.khu.bbangting.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto { //주문 목록 불러오기

    private String breadName;
    private int price;
    private int quantity;
    private Timestamp orderDate;

    @Builder
    public OrderHistDto(String breadName, int price, int quantity, Timestamp orderDate) {
        this.breadName = breadName;
        this.price = price;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    private List<OrderHistDto> orderList = new ArrayList<>();

}
