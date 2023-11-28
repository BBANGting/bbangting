package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.order.dto.OrderHistDto;
import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.store.dto.StoreInfoDto;
import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserUpdateDto;
import com.khu.bbangting.domain.user.service.UserService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.order.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final FollowService followService;
    private final UserService userService;


    // 마이페이지 호출
    @Getter @Setter
    static class Result<T> {
        private List<StoreInfoDto> followingList;
        private User user;

        public Result(List<StoreInfoDto> followingList, User user) {
            this.followingList = followingList;
            this.user = user;
        }
    }

    @GetMapping("/myPage/{userId}")
    public ResponseEntity<?> myPageForm(@PathVariable Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 팔로잉 정보
        List<StoreInfoDto> followingList = followService.getFollowingList(userId);
        // 유저 정보
        User userInfo = userService.findByEmail(user.getEmail());

        return ResponseEntity.ok().body(new Result<>(followingList, userInfo));
    }

    // 2. 회원정보 수정 (비밀번호 & 닉네임)
    @GetMapping("/myPage/{userId}/password")
    public ResponseEntity<?> passwordUpdateForm() {

        return ResponseEntity.ok().build();

    }

    @GetMapping("/myPage/{userId}/nickname")
    public ResponseEntity<?> nicknameUpdateForm() {

        return ResponseEntity.ok().build();
    }


    // 3. 주문내역 페이지 (주문내역 정보 포함)
    @GetMapping("/myPage/{userId}/order")
    public ResponseEntity<?> userOrderHistForm(@PathVariable Long userId) {

        List<OrderHistDto> orderList = orderService.getOrderList(userId);

        return ResponseEntity.ok(orderList);

    }

    // 4. 결제수단 -> 보류
    @GetMapping("/myPage/{userId}/payment")
    public ResponseEntity<?> paymentForm() {

        return ResponseEntity.ok().build();
    }

    // 5. 리뷰관리 -> 보류
    @GetMapping("/myPage/{userId}/review")
    public ResponseEntity<?> reviewForm() {

        return ResponseEntity.ok().build();
    }

}
