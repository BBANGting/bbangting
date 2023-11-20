package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.order.dto.OrderHistDto;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;
    private final OrderService orderService;

    // 마이페이지 호출 (유저 정보 포함)
    @GetMapping("/myPage/{userId}")
    public String myPageForm(@PathVariable String email, Model model, @AuthenticationPrincipal User userInfo) {

        User byEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        model.addAttribute(byEmail);
        model.addAttribute("userInfo", byEmail.equals(userInfo));   //전달된 객체와 DB에서 조회한 객체가 같으면 -> 인증된 사용자

        return "/myPageForm";
    }

    // 2. 회원정보 수정

    // 3. 주문내역 페이지 (주문내역 정보 포함)
    @GetMapping({"/myPage/order", "/myPage/order/{page}"})
    public String userOrderHistForm(@PathVariable("page") Optional<Integer> page, String email) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(email, pageable);

        return "order/orderHistForm";
    }

    // 4. 결제수단 -> 보류
    @GetMapping("/myPage/payment")
    public String paymentForm() {

        return "myPage/paymentForm";
    }

    // 5. 리뷰관리 -> 보류

    // 6. 팔로잉
}
