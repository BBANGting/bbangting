package com.khu.bbangting.domain.review.controller;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final UserRepository userRepository;
    private final BreadRepository breadRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/review/{userId}/{breadId}")
    public ResponseEntity<ReviewFormDto> addReview(@PathVariable Long userId, @PathVariable Long breadId, @RequestBody ReviewFormDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_NOT_FOUND));

        ReviewFormDto newReview = reviewService.register(user, bread, requestDto);

        return ResponseEntity.ok(newReview);
    }

    // 리뷰 수정
    @PutMapping("/review/{userId}/{reviewId}")
    public ResponseEntity<ReviewUpdateFormDto> modifyReview(@PathVariable Long userId, @PathVariable Long reviewId,
                                                            @RequestBody ReviewUpdateFormDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        ReviewUpdateFormDto updateReview = reviewService.modify(user, reviewId, requestDto);

        return ResponseEntity.ok(updateReview);
    }

    // 리뷰 삭제
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<?> removeReview(@PathVariable Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        reviewService.remove(reviewId);

        return ResponseEntity.ok().build();
    }

}
