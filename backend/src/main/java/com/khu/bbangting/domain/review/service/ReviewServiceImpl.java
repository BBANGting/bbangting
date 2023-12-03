package com.khu.bbangting.domain.review.service;

import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.review.dto.ReviewUpdateFormDto;
import com.khu.bbangting.domain.review.model.Review;
import com.khu.bbangting.domain.review.repository.ReviewRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
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
    public Long register(User user, ReviewFormDto reviewFormDto) {

        if (reviewRepository.existsByUserIdAndBreadId(user.getId(), reviewFormDto.getBreadId())) {
            throw new CustomException(ErrorCode.REVIEW_IS_EXIST);}

        Review breadReview = toEntity(user, reviewFormDto);

        reviewRepository.save(breadReview);

        return breadReview.getId();
    }

    @Override
    public void modify(User user, Long reviewId, ReviewUpdateFormDto reviewUpdateFormDto) {

        Optional<Review> result = reviewRepository.findById(reviewId);

        if (result.isPresent()) {

            Review breadReview = result.get();
            breadReview.changeRating(reviewUpdateFormDto.getRating());
            breadReview.changeContent(reviewUpdateFormDto.getContent());

            reviewRepository.save(breadReview);
        }

    }

    @Override
    public void remove(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }
}
