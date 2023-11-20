package com.khu.bbangting.domain.review.controller;

import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성
     */
    @PostMapping("/review/{breadId}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewFormDto reviewFormDto) {

        Long reviewId = reviewService.register(reviewFormDto);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/review/{breadId}/{reviewId}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewId) {

        reviewService.remove(reviewId);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    /**
     * 리뷰수정 페이지
     */

    /**
     * 리뷰 수정
     */
    @PutMapping("/review/{breadId}/{reviewId}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewId,
                                             @RequestBody ReviewFormDto reviewFormDto) {

        reviewService.modify(reviewFormDto);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

}
