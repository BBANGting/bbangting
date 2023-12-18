package com.khu.bbangting.domain.order.controller;

import com.khu.bbangting.config.jwt.SecurityUtils;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final UserRepository userRepository;
    private final BreadRepository breadRepository;
    private final OrderService orderService;

    // 주문하기
    @PostMapping("/order/{breadId}")
    public ResponseEntity<String> addOrder(@PathVariable Long breadId, @RequestBody @Valid OrderFormDto requestDto) {

        String userEmail = SecurityUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_NOT_FOUND));

        if (user.isBanUser(user)) {
            user.setType(Type.RESTRICTED);
            throw new CustomException(ErrorCode.BAN_USER);
        }

        orderService.addOrder(user, bread, requestDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("주문 성공");
    }

    // 주문 취소하기
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("주문 취소 완료");
    }

}
