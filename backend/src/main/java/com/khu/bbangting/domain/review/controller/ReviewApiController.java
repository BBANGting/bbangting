package com.khu.bbangting.domain.review.controller;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.repository.OrderRepository;
import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.review.dto.ReviewUpdateFormDto;
import com.khu.bbangting.domain.review.model.Review;
import com.khu.bbangting.domain.review.repository.ReviewRepository;
import com.khu.bbangting.domain.review.service.ReviewService;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final UserRepository userRepository;
    private final BreadRepository breadRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/review/{breadId}")
    public ResponseEntity<String> addReview(@AuthenticationPrincipal User user, @PathVariable Long breadId, @RequestBody ReviewFormDto requestDto) {

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_NOT_FOUND));

        Order order = orderRepository.findByUserIdAndBreadId(user.getId(), breadId);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 작성 실패");
        }

        reviewService.register(user, bread, requestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("리뷰 작성 성공");

    }

    // 리뷰 수정
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<String> modifyReview(@AuthenticationPrincipal User user, @PathVariable Long reviewId,
                                                            @RequestBody ReviewUpdateFormDto requestDto) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        reviewService.modify(user, reviewId, requestDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("리뷰 수정 성공");
    }

    // 리뷰 삭제
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<String> removeReview(@PathVariable Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        reviewService.remove(reviewId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("리뷰 삭제 성공");
    }

}
