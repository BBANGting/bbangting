package com.khu.bbangting.domain.review.service;

import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.review.dto.ReviewUpdateFormDto;
import com.khu.bbangting.domain.review.model.Review;
import com.khu.bbangting.domain.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewService {

    // 빵 상세정보에서 리뷰들 가져오기
    List<ReviewFormDto> getListOfBread(Long breadId);

    //리뷰 작성
    ReviewFormDto register(User user, Bread bread, ReviewFormDto reviewFormDto);

    //리뷰 수정
    ReviewUpdateFormDto modify(User user, Long reviewId, ReviewUpdateFormDto reviewUpdateFormDto);

    //리뷰 삭제
    void remove(Long reviewId);

    default Review toEntity(User user, Bread bread, ReviewFormDto reviewFormDto) {

        Review breadReview = Review.builder()
                .rating(reviewFormDto.getRating())
                .content(reviewFormDto.getContent())
                .createdDate(LocalDateTime.now())
                .bread(Bread.builder().id(bread.getId()).build())
                .user(user)
                .build();

        return breadReview;
    }

    default ReviewFormDto fromReview(Review review) {

        ReviewFormDto breadReviewDto = ReviewFormDto.builder()
                .nickname(review.getUser().getNickname())
                .rating(review.getRating())
                .content(review.getContent())
                .build();

        return breadReviewDto;
    }
}
