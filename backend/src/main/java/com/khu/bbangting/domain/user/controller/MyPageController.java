package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.order.dto.OrderHistDto;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final FollowService followService;

    // 마이페이지 호출 (유저 정보 포함)
    @GetMapping("/myPage/{userId}")
    public String myPageForm(@PathVariable Long userId,  Model model) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 마이페이지 -> 팔로잉 목록 호출
        List<StoreInfoDto> followingList = followService.getFollowingList(userId);
        log.info(followingList.toString());
        model.addAttribute("followingList", followingList);

        // 마이페이지 -> 유저 정보 호출
        model.addAttribute(user);

        return "myPage";
    }

    // 2. 회원정보 수정 (비밀번호 & 닉네임)
    @GetMapping("/myPage/{userId}/password")
    public String passwordUpdateForm(@PathVariable Long userId, Model model) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        model.addAttribute(user);
        model.addAttribute(new PasswordUpdateDto());

        return "myPage/setting/password";
    }

    @GetMapping("/myPage/{userId}/nickname")
    public String nicknameUpdateForm(@PathVariable Long userId, Model model) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        model.addAttribute(user);
        model.addAttribute(new NicknameUpdateDto());
        return "myPage/setting/nickname";
    }


    // 3. 주문내역 페이지 (주문내역 정보 포함)
    @GetMapping("/myPage/{userId}/order")
    public String userOrderHistForm(@PathVariable Long userId, Model model) {

        List<OrderHistDto> orderList = orderService.getOrderList(userId);
        log.info(orderList.toString());
        model.addAttribute("orderList", orderList);

        return "myPage/order";
    }

    // 4. 결제수단 -> 보류
    @GetMapping("/myPage/{userId}/payment")
    public String paymentForm() {

        return "myPage/paymentForm";
    }

    // 5. 리뷰관리 -> 보류
    @GetMapping("/myPage/{userId}/review")
    public String reviewForm() {

        return "myPage/review";
    }

}
