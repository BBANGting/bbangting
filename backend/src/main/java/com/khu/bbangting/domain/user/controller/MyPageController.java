package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.order.dto.OrderHistDto;
import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.review.service.ReviewService;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.user.dto.UserInfoDto;
import com.khu.bbangting.domain.user.service.MyPageService;
import com.khu.bbangting.domain.order.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final OrderService orderService;
    private final MyPageService myPageService;
    private final FollowService followService;
    private final ReviewService reviewService;

    // 마이페이지 호출 (유저 정보 포함)
    @GetMapping("/myPage")
    public ResponseEntity<Result<List<?>>> getMyPage(Authentication authentication) {

        List<UserInfoDto> userInfo = myPageService.getUserInfo(authentication);

        List<StoreInfoDto> followingList = followService.getFollowingList(authentication);

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
    public ResponseEntity<?> passwordUpdateForm(Authentication authentication) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/myPage/nickname")
    public ResponseEntity<?> nicknameUpdateForm(Authentication authentication) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    // 3. 주문내역 페이지 (주문내역 정보 포함)
    @GetMapping("/myPage/order")
    public ResponseEntity<List<OrderHistDto>> userOrderHistForm(Authentication authentication) {

        List<OrderHistDto> orderList = orderService.getOrderList(authentication);

        return ResponseEntity.ok(orderList);
    }

    // 4. 결제수단 -> 보류
    @GetMapping("/myPage/payment")
    public ResponseEntity<?> paymentForm() {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // 5. 작성한 리뷰내역
    @GetMapping("/myPage/review")
    public ResponseEntity<List<ReviewFormDto>> reviewPage(Authentication authentication) {
        List<ReviewFormDto> reviewListDto = reviewService.getListOfUserReview(authentication);

        return ResponseEntity.ok(reviewListDto);
    }

}
