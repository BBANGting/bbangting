package com.khu.bbangting.domain.order.controller;

import com.khu.bbangting.domain.order.dto.OrderFormDto;
import com.khu.bbangting.domain.order.service.OrderService;
import com.khu.bbangting.domain.user.model.Type;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final UserRepository userRepository;
    private final OrderService orderService;

    // 주문하기
    @PostMapping("/order/{userId}/{breadId}")
    public String addOrder(@PathVariable Long userId, @RequestBody @Valid OrderFormDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.isBanUser(user)) {
            user.setType(Type.RESTRICTED);
            throw new CustomException(ErrorCode.BAN_USER);
        }

        orderService.addOrder(user, requestDto);

        return "redirect:/bread/{breadId}";
    }

    // 주문 취소하기
    @DeleteMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return "redirect:/myPage/{userId}/order";
    }

}
