package com.khu.bbangting.domain.order.controller;

import com.khu.bbangting.domain.order.dto.OrderFormDto;
import com.khu.bbangting.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    /**
     * 주문
     */
    @PostMapping("/order")
    public ResponseEntity<?> addOrder(@RequestBody @Valid OrderFormDto requestDto) {

        orderService.addOrder(requestDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 주문 취소
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.ok().build();
    }

}
