package com.khu.bbangting.service;

import com.khu.bbangting.dto.ReviewDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Review;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.repository.ReviewRepository;
import com.khu.bbangting.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BreadRepository breadRepository;
    private final UserRepository userRepository;

    /* 리뷰 작성 */
    @Transactional
    public Long addReview(ReviewDto reviewDto) {
        Bread bread = breadRepository.findById(reviewDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        User user = userRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다."));

        Review createdReview = Review.createReview(user, bread);
        createdReview.setRating(reviewDto.getRating());
        createdReview.setContent(reviewDto.getContent());
        createdReview.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));

        reviewRepository.save(createdReview);
        return createdReview.getId();
    }
}