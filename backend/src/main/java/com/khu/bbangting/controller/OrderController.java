package com.khu.bbangting.controller;

import com.khu.bbangting.dto.order.OrderFormDto;
import com.khu.bbangting.dto.order.OrderHistDto;
import com.khu.bbangting.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController {

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

    /**
     * 주문 내역 조회
     */
    @GetMapping({"/myPage/order", "/myPage/order/{page}"})
    public String userOrderHist(@PathVariable("page") Optional<Integer> page,  String email) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(email, pageable);

        return "order/orderHist";
    }

}
