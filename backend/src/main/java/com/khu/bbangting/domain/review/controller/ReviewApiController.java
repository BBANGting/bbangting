package com.khu.bbangting.domain.review.controller;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.bread.repository.BreadRepository;
import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.review.dto.ReviewUpdateFormDto;
import com.khu.bbangting.domain.review.service.ReviewService;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final UserRepository userRepository;
    private final BreadRepository breadRepository;
    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/review/{userId}/{breadId}")
    public ResponseEntity<?> addReview(@PathVariable Long userId, @PathVariable Long breadId, @RequestBody ReviewFormDto reviewFormDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Bread bread = breadRepository.findById(breadId)
                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_NOT_FOUND));

        return ResponseEntity.ok(reviewService.register(user, bread, reviewFormDto));
    }

    // 리뷰 수정
    @PutMapping("/review/{userId}/{reviewId}")
    public ResponseEntity<?> modifyReview(@PathVariable Long userId, @PathVariable Long reviewId, @RequestBody ReviewUpdateFormDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return ResponseEntity.ok(reviewService.modify(user, reviewId, request));
    }

    // 리뷰 삭제
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<?> removeReview(@PathVariable Long reviewId) {

        reviewService.remove(reviewId);

        return ResponseEntity.ok().build();
    }

}
