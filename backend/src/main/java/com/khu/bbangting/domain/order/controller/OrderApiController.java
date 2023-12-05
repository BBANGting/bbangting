package com.khu.bbangting.domain.order.controller;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.order.dto.OrderFormDto;
import com.khu.bbangting.domain.order.service.OrderService;
import com.khu.bbangting.domain.user.model.Type;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final UserRepository userRepository;
    private final BreadRepository breadRepository;
    private final OrderService orderService;

    // 주문하기
    @PostMapping("/order/{userId}/{breadId}")
    public ResponseEntity<?> addOrder(@PathVariable Long userId, @PathVariable Long breadId, @RequestBody @Valid OrderFormDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_NOT_FOUND));

        if (user.isBanUser(user)) {
            user.setType(Type.RESTRICTED);
            throw new CustomException(ErrorCode.BAN_USER);
        }

        orderService.addOrder(user, bread, requestDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // 주문 취소하기
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
