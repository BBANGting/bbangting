package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.config.jwt.SecurityUtils;
import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.order.dto.OrderHistDto;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.service.MyPageService;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.order.service.OrderService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Result<List<?>>> getMyPage() {

        String userEmail = SecurityUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<UserResponseDto> userInfo = myPageService.getUserInfo(user);

        List<StoreInfoDto> followingList = followService.getFollowingList(user.getId());

        return ResponseEntity.ok().body(new Result<>(followingList, userInfo));
    }

    @Getter
    @Setter
    static class Result<T> {
        private T followingList;
        private T userInfo;

        public Result(T followingList, T userInfo) {
            this.followingList = followingList;
            this.userInfo = userInfo;
        }
    }

    // 2. 회원정보 수정 (비밀번호 & 닉네임)
    @GetMapping("/myPage/password")
    public ResponseEntity<?> passwordUpdateForm() {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/myPage/nickname")
    public ResponseEntity<?> nicknameUpdateForm() {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    // 3. 주문내역 페이지 (주문내역 정보 포함)
    @GetMapping("/myPage/order")
    public ResponseEntity<List<OrderHistDto>> userOrderHistForm() {

        String userEmail = SecurityUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
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
