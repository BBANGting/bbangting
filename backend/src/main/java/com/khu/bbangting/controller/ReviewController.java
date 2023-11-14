package com.khu.bbangting.controller;

import com.khu.bbangting.dto.review.ReviewFormDto;
import com.khu.bbangting.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 빵 상세페이지에서 리뷰 목록 조회
     */
    @GetMapping("/bread/{breadId}/review")
    public ResponseEntity<List<ReviewFormDto>> getReviewList(@PathVariable("breadId") Long breadId) {

        List<ReviewFormDto> reviewFormDtoList = reviewService.getListOfBread(breadId);

        return new ResponseEntity<>(reviewFormDtoList, HttpStatus.OK);
    }

    /**
     * 리뷰 작성
     */
    @PostMapping("/review/{breadId}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewFormDto reviewFormDto) {

        Long reviewId = reviewService.register(reviewFormDto);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    /**
     * 리뷰 수정
     */
    @PutMapping("/review/{breadId}/{reviewId}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewId,
                                             @RequestBody ReviewFormDto reviewFormDto) {

        reviewService.modify(reviewFormDto);

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

}
