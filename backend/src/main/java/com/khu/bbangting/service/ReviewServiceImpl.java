package com.khu.bbangting.service;

import com.khu.bbangting.dto.review.ReviewFormDto;
import com.khu.bbangting.model.Bread;
import com.khu.bbangting.model.Review;
import com.khu.bbangting.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewFormDto> getListOfBread(Long breadId) {

        Bread bread = Bread.builder().id(breadId).build();

        List<Review> result = reviewRepository.findByBread(bread);

        return result.stream().map(breadReview -> fromReview(breadReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewFormDto reviewFormDto) {

        Review breadReview = toEntity(reviewFormDto);

        reviewRepository.save(breadReview);

        return breadReview.getId();
    }

    @Override
    public void modify(ReviewFormDto reviewFormDto) {

        Optional<Review> result = reviewRepository.findById(reviewFormDto.getReviewId());

        if (result.isPresent()) {

            Review breadReview = result.get();
            breadReview.changeRating(reviewFormDto.getRating());
            breadReview.changeContent(reviewFormDto.getContent());

            reviewRepository.save(breadReview);
        }

    }

    @Override
    public void remove(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }
}
