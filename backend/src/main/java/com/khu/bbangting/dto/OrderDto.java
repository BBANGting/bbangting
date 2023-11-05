package com.khu.bbangting.dto;

import lombok.Data;

@Data
public class OrderDto { //주문할 상품의 정보

    private Long breadId;
    private int quantity;
}

