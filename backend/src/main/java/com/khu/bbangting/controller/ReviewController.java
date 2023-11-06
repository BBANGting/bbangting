package com.khu.bbangting.controller;

import com.khu.bbangting.dto.ResponseDto;
import com.khu.bbangting.dto.ReviewDto;
import com.khu.bbangting.dto.UserFormDto;
import com.khu.bbangting.model.Review;
import com.khu.bbangting.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseDto<Integer> addReview(@RequestBody ReviewDto reviewDto) {

        System.out.println("ReviewController: save 함수 호출됨");
        reviewService.addReview(reviewDto);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}