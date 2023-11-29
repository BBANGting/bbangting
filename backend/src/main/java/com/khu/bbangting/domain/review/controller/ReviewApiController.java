package com.khu.bbangting.domain.review.controller;

import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.review.dto.ReviewUpdateFormDto;
import com.khu.bbangting.domain.review.service.ReviewService;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final UserRepository userRepository;
    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/review/{userId}/{breadId}")
    public String addReview(@PathVariable Long userId, @RequestBody ReviewFormDto reviewFormDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        reviewService.register(user, reviewFormDto);

        return "/myPage/{userId}/order";
    }

    // 리뷰 수정
    @PutMapping("/review/{userId}/{reviewId}")
    public String modifyReview(@PathVariable Long userId, @PathVariable Long reviewId,
                               @RequestBody ReviewUpdateFormDto reviewUpdateFormDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        reviewService.modify(user, reviewId, reviewUpdateFormDto);

        return "/myPage/{userId}/order";
    }

    // 리뷰 삭제
    @DeleteMapping("/review/{reviewId}")
    public String removeReview(@PathVariable Long reviewId) {

        reviewService.remove(reviewId);

        return "/myPage/{userId}/order";
    }

}
