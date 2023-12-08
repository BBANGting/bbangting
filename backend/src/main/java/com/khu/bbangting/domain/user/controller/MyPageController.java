package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.order.dto.OrderHistDto;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.user.model.UserDetailsImpl;
import com.khu.bbangting.domain.user.service.MyPageService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.order.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final MyPageService myPageService;
    private final FollowService followService;

    // 마이페이지 호출 (유저 정보 포함)
    @GetMapping("/myPage")
    public ResponseEntity<Result<List<StoreInfoDto>>> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = myPageService.getUserInfo(userDetails);

        List<StoreInfoDto> followingList = followService.getFollowingList(userDetails.getId());

        return ResponseEntity.ok().body(new Result<>(followingList, user));
    }

    @Getter
    @Setter
    static class Result<T> {
        private T followingList;
        private User user;

        public Result(T followingList, User user) {
            this.followingList = followingList;
            this.user = user;
        }
    }

    // 2. 회원정보 수정 (비밀번호 & 닉네임)
    @GetMapping("/myPage/password")
    public ResponseEntity<?> passwordUpdateForm(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/myPage/nickname")
    public ResponseEntity<?> nicknameUpdateForm(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    // 3. 주문내역 페이지 (주문내역 정보 포함)
    @GetMapping("/myPage/order")
    public ResponseEntity<List<OrderHistDto>> userOrderHistForm(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<OrderHistDto> orderList = orderService.getOrderList(user.getId());

        return ResponseEntity.ok(orderList);
    }

    // 4. 결제수단 -> 보류
    @GetMapping("/myPage/payment")
    public ResponseEntity<?> paymentForm() {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // 5. 리뷰관리 -> 보류
    @GetMapping("/myPage/review")
    public ResponseEntity<?> reviewForm() {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
